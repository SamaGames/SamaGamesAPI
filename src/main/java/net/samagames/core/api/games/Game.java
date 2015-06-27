package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.IGameManager;
import net.samagames.api.games.Status;
import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.templates.EarningMessageTemplate;
import net.samagames.core.APIPlugin;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Game<GAMEPLAYER extends GamePlayer>
{
    protected final IGameManager gameManager;

    protected final String gameName;
    protected final Class<GAMEPLAYER> gamePlayerClass;
    protected final HashMap<UUID, GAMEPLAYER> gamePlayers;
    protected final BukkitTask beginTimer;

    protected ICoherenceMachine coherenceMachine;
    protected Status status;

    public Game(String gameName, Class<GAMEPLAYER> gamePlayerClass)
    {
        this.gameManager = SamaGamesAPI.get().getGameManager();

        this.gameName = gameName;
        this.gamePlayerClass = gamePlayerClass;
        this.gamePlayers = new HashMap<>();
        this.beginTimer = Bukkit.getScheduler().runTaskAsynchronously(APIPlugin.getInstance(), new BeginTimer(this));

        this.status = Status.WAITING_FOR_PLAYERS;
    }

    /**
     * Appelée quand le lancement du jeu est attendu, soit via le compte à rebours auto-géré, soit via la commande
     * /start
     *
     * Appel à la super-méthode si Override : Requis
     */
    public void startGame()
    {
        this.beginTimer.cancel();
    }

    public void handlePostRegistration()
    {
        this.coherenceMachine = this.gameManager.getCoherenceMachine();
    }

    /**
     * Appelée quand un joueur se connecte au serveur, le boolean représente si le joueur est nouveau ou se
     * connecte pendant le jeu via le système de reconnexion (Nouveau -> false, Reconnexion -> True).
     * Vous pouvez traiter vos actions dans votre classe de joueur via la méthode "handleLogin"
     *
     * Appel à la super-méthode si Override : Requis
     */
    public void handleLogin(Player player, boolean reconnect)
    {
        try
        {
            GAMEPLAYER gamePlayerObject = this.gamePlayerClass.getConstructor(Player.class).newInstance(player);
            gamePlayerObject.handleLogin(reconnect);

            this.gamePlayers.put(player.getUniqueId(), gamePlayerObject);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Appelée quand un modérateur se connecte
     *
     * Appel à la super-méthode : Requis
     */
    public void handleModeratorLogin(Player player)
    {
        for(UUID gamePlayerUUID : this.gamePlayers.keySet())
            Bukkit.getPlayer(gamePlayerUUID).hidePlayer(player);

        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GREEN + "Vous êtes invisibles aux yeux de tous, attention à vos actions !");
    }

    /**
     * Appelée quand un joueur se déconnecte, il est inutile de passer au dessus de cette méthode dans votre classe
     * de jeu, il suffit d'ajouter vos actions dans votre classe de joueur via la méthode "handleLogout"
     *
     * Appel à la super-méthode si Override : Requis
     */
    public void handleLogout(Player player)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
        {
            if(!this.gamePlayers.get(player.getUniqueId()).isSpectator())
            {
                if(this.gameManager.isReconnectAllowed() && this.status == Status.IN_GAME)
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerDisconnected(player, this.gameManager.getMaxReconnectTime());
                }
                else
                {
                    this.gameManager.getCoherenceMachine().getMessageManager().writePlayerQuited(player);
                    this.getPlayer(player.getUniqueId()).setSpectator();
                }
            }

            this.gamePlayers.get(player.getUniqueId()).handleLogout();

            if(this.status != Status.IN_GAME)
                this.gamePlayers.remove(player.getUniqueId());
        }
    }

    /**
     * Appelée quand un joueur se reconnecte pendant le jeu via le système de reconnexion, il est inutile de
     * passer au dessus de cette méthode dans votre classe de jeu, il suffit d'ajouter vos actions dans votre
     * classe de joueur via la méthode "handleLogin"
     *
     * Appel à la super-méthode si Override : Requis
     */
    public void handleReconnect(Player player)
    {
        this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnected(player);
        this.gamePlayers.get(player.getUniqueId()).handleLogin(true);
    }

    /**
     * Appelée quand un le temps de reconnexion d'un joueur expire.
     *
     * Appel à la super-méthode si Override : Requis
     */
    public void handleReconnectTimeOut(Player player)
    {
        this.setSpectator(player);
        this.gameManager.getCoherenceMachine().getMessageManager().writePlayerReconnectTimeOut(player);
    }

    /**
     * FONCTION SPECIALE
     *
     * Votre fonction de fin de jeu n'est pas crée de base, c'est à dire que vous devez la créer de votre côté.
     * Comme tout jeu, il y a un gagnant et/ou un classement. La CohérenceMachine vous offre ses fonctions via ses
     * modèles. Renseignez vous dans le TemplateManager de la CohérenceMachine. Ceci est obligatoire.
     *
     * Vous appelerez ensuite cette fonction qui finiera le travail du plugin, c'est à dire afficher les gains
     * des joueurs ainsi que de fermer le serveur.
     */
    public void handleGameEnd()
    {
        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () ->
        {
            for (UUID playerUUID : this.gamePlayers.keySet())
            {
                if (Bukkit.getPlayer(playerUUID) != null)
                {
                    EarningMessageTemplate earningMessageTemplate = this.coherenceMachine.getTemplateManager().getEarningMessageTemplate();
                    earningMessageTemplate.execute(Bukkit.getPlayer(playerUUID), this.getPlayer(playerUUID).getCoins(), this.getPlayer(playerUUID).getStars());
                }
            }
        }, 20L * 3);

        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), () ->
        {
            for (Player player : Bukkit.getOnlinePlayers())
                this.gameManager.kickPlayer(player, null);
        }, 20L * 10);

        Bukkit.getScheduler().runTaskLater(APIPlugin.getInstance(), Bukkit::shutdown, 20L * 11);
    }

    /**
     * Fonction à appeler pour donner des pièces à un joueur
     *
     * @param player Le joueur en question
     * @param coins Le nombre de pièces
     * @param reason La raison de l'ajout
     */
    public void addCoins(Player player, int coins, String reason)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).addCoins(coins, reason);
        else
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditCoins(coins, reason, true);
    }

    /**
     * Fonction à appeler pour donner des étoiles à un joueur
     *
     * @param player Le joueur en question
     * @param stars Le nombre d'étoiles
     * @param reason La raison de l'ajout
     */
    public void addStars(Player player, int stars, String reason)
    {
        if(this.gamePlayers.containsKey(player.getUniqueId()))
            this.gamePlayers.get(player.getUniqueId()).addStars(stars, reason);
        else
            SamaGamesAPI.get().getPlayerManager().getPlayerData(player.getUniqueId()).creditStars(stars, reason, true);
    }

    /**
     * Définir un joueur comme spectateur
     *
     * @param player Le joueur en question
     */
    public void setSpectator(Player player)
    {
        this.gamePlayers.get(player.getUniqueId()).setSpectator();
    }

    public void setStatus(Status status)
    {
        this.status = status;
        this.gameManager.refreshArena();
    }

    public String getGameName()
    {
        return this.gameName;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public GAMEPLAYER getPlayer(UUID player)
    {
        if(this.gamePlayers.containsKey(player))
            return this.gamePlayers.get(player);
        else
            return null;
    }

    public HashMap<UUID, GAMEPLAYER> getInGamePlayers()
    {
        HashMap<UUID, GAMEPLAYER> temp = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
            if(!this.gamePlayers.get(key).isSpectator())
                temp.put(key, this.gamePlayers.get(key));

        return temp;
    }

    public HashMap<UUID, GAMEPLAYER> getSpectatorPlayers()
    {
        HashMap<UUID, GAMEPLAYER> temp = new HashMap<>();

        for(UUID key : this.gamePlayers.keySet())
            if(this.gamePlayers.get(key).isSpectator())
                temp.put(key, this.gamePlayers.get(key));

        return temp;
    }

    public int getConnectedPlayers()
    {
        return this.getInGamePlayers().size();
    }

    /**
     * Savoir si un joueur peut-entrer en jeu
     *
     * @param player Le joueur en question
     * @param reconnect Si le joueur se reconnecte pendant le jeu ou non
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    public Pair<Boolean, String> canJoinGame(UUID player, boolean reconnect)
    {
        return Pair.of(true, "");
    }

    /**
     * Savoir si une partie peut entrer entière en jeu
     *
     * @param partyMembers Les joueurs de la partie
     * @return Un objet Pair de la forme <Autorisé ? (Oui/Non), Raison du refus (Si refus)>
     */
    public Pair<Boolean, String> canPartyJoinGame(Set<UUID> partyMembers)
    {
        return Pair.of(true, "");
    }
}