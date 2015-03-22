package net.samagames.core.api.gameapi.themachine.messages;

import net.samagames.api.gameapi.themachine.CoherenceMachine;
import net.samagames.api.gameapi.themachine.messages.Message;
import net.samagames.api.gameapi.themachine.messages.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManagerImpl implements MessageManager
{
    private final CoherenceMachine machine;

    public MessageManagerImpl(CoherenceMachine machine)
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
        builder.append(ChatColor.RED).append(this.machine.getGame().getMaxPlayers());

        if(this.machine.getGame().getConnectedPlayers() > this.machine.getGame().getMaxPlayers())
            builder.append(ChatColor.GREEN).append(" [Slots VIP]");

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
        return new Message(ChatColor.YELLOW + "Début du jeu dans " + ChatColor.RED + remainingTime, this.machine.getGameTag()).displayToAll();
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
    public Message writePlayerQuitted(Player player)
    {
        return new Message(ChatColor.WHITE + player.getName() + " s'est déconnecté du jeu.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerDisconnected(Player player, int remainingTime)
    {
        return new Message(ChatColor.RED + player.getName() + " s'est déconnté ! Il a " + remainingTime + " minutes pour revenir.", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message writePlayerReconnedted(Player player)
    {
        return new Message(ChatColor.GREEN + player.getName() + " s'est reconnecté !", this.machine.getGameTag()).displayToAll();
    }

    @Override
    public Message getArenaFull()
    {
        return new Message(ChatColor.RED + "L'arène est pleine.");
    }
}
