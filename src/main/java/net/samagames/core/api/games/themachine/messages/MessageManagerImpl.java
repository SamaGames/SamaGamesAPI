package net.samagames.core.api.games.themachine.messages;

import net.samagames.api.games.themachine.ICoherenceMachine;
import net.samagames.api.games.themachine.messages.Message;
import net.samagames.api.games.themachine.messages.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManagerImpl implements MessageManager
{
    private final ICoherenceMachine machine;

    public MessageManagerImpl(ICoherenceMachine machine)
    {
        this.machine = machine;
    }

    @Override
    public Message writePlayerJoinToAll(Player player)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.YELLOW).append(player.getName());
        builder.append(" a rejoint la partie ! ");
        builder.append(ChatColor.GRAY).append("[");
        builder.append(ChatColor.RED).append(this.machine.getGame().getConnectedPlayers());
        builder.append(ChatColor.DARK_GRAY).append("/");
        builder.append(ChatColor.RED).append(this.machine.getGameProperties().getMaxSlots());
        builder.append(ChatColor.GRAY).append("]");

        return new Message(builder.toString(), this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writeWelcomeInGameToPlayer(Player player)
    {
        return new Message(ChatColor.GOLD + "\nBienvenue en " + ChatColor.RED + this.machine.getGame().getGameName() + ChatColor.GOLD + " !").display(player);
    }

    @Override
    public Message writeGameStartIn(int remainingTime)
    {
        return new Message(ChatColor.YELLOW + "Début du jeu dans " + ChatColor.RED + remainingTime + " secondes.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writeNotEnougthPlayersToStart()
    {
        return new Message(ChatColor.RED + "Il n'y a plus assez de joueurs pour commencer.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writeGameStart()
    {
        return new Message("La partie commence !", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerQuited(Player player)
    {
        return new Message(ChatColor.WHITE + player.getName() + " s'est déconnecté du jeu.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerDisconnected(Player player, int remainingTime)
    {
        return new Message(ChatColor.RED + player.getName() + " s'est déconnecté ! Il a " + remainingTime + " minutes pour revenir.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerReconnected(Player player)
    {
        return new Message(ChatColor.GREEN + player.getName() + " s'est reconnecté !", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerReconnectTimeOut(Player player)
    {
        return new Message(ChatColor.RED + player.getName() + " ne s'est pas reconnecté à temps !");
    }

    @Override
    public Message getArenaFull()
    {
        return new Message(ChatColor.RED + "L'arène est pleine.");
    }
}
