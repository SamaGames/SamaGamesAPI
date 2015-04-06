package net.samagames.core.api.games;

import net.samagames.api.games.GameManager;
import net.samagames.api.games.IManagedGame;
import net.samagames.api.games.IMasterControlledGame;
import net.samagames.api.games.Status;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import net.samagames.api.network.ResponseType;
import net.samagames.permissionsbukkit.PermissionsBukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO : REWRITE THIS

public class GameLoginHandler implements JoinHandler
{
    private final GameManager api;
    private HashSet<UUID> partyJoining = new HashSet<>();

    public GameLoginHandler(GameManager api)
    {
        this.api = api;
    }

    @Override
    public JoinResponse onPreJoinParty(Set<UUID> players, JoinResponse response) {
        if (this.api.getGame() != null) {
            if (!response.isAllowed()) {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    response = ((IMasterControlledGame) this.api.getGame()).onPreJoinParty(players, response);
            } else {

                IManagedGame game = this.api.getGame();

                if (game.getStatus() == Status.IN_GAME)
                    response.disallow(ResponseType.DENY_IN_GAME);
                else if (game.getStatus() == Status.STARTING)
                    response.disallow(ResponseType.DENY_NOT_READY);
                else if (game.getConnectedPlayers() + players.size() > game.getTotalMaxPlayers())
                    response.disallow(ResponseType.DENY_FULL);
                else if (game.getConnectedPlayers() + players.size() > game.getMaxPlayers() && ! PermissionsBukkit.hasPermission(players.iterator().next(), "games.joinvip"))
                    response.disallow(ResponseType.DENY_VIPONLY);

                if (this.api.getGame() instanceof IMasterControlledGame)
                    response = ((IMasterControlledGame) this.api.getGame()).onPreJoinParty(players, response);
            }

            if (response.isAllowed()) {
                partyJoining.addAll(players);
            }
        }

        return response;
    }

    @Override
    public JoinResponse onLogin(UUID player, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed() || partyJoining.contains(player)) // On épargne les checks aux joueurs qui rejoignent en party
            {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).onLogin(player, response);
                else
                    return response;
            }

            IManagedGame game = this.api.getGame();

            if (game.getStatus() == Status.IN_GAME)
                response.disallow(ResponseType.DENY_IN_GAME);
            else if (game.getStatus() == Status.STARTING)
                response.disallow(ResponseType.DENY_NOT_READY);
            else if (game.getConnectedPlayers() > game.getTotalMaxPlayers() && ! PermissionsBukkit.hasPermission(player, "games.joinfull"))
                response.disallow(ResponseType.DENY_FULL);
            else if (game.getConnectedPlayers() > game.getMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinvip"))
                response.disallow(ResponseType.DENY_VIPONLY);

            if(this.api.isReconnectAllowed())
                response.allow(); // Heu t'es sûr de ça ? oO Ca annule juste tous les cheks précédents...

            if(!this.api.isWaited(player))
            {
                if (this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).onLogin(player, response);
            }
        }
        return response;
    }

    @Override
    public JoinResponse onJoin(Player player, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed())
            {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).onJoin(player, response);
                else
                    return response;
            }

			IManagedGame info = this.api.getGame();

			if (info.getStatus() == Status.IN_GAME)
				response.disallow(ResponseType.DENY_IN_GAME);
			else if (info.getStatus() == Status.STARTING)
				response.disallow(ResponseType.DENY_NOT_READY);
			else if (info.getConnectedPlayers() > info.getTotalMaxPlayers() && ! PermissionsBukkit.hasPermission(player, "games.joinfull"))
				response.disallow(ResponseType.DENY_FULL);

            if(this.api.isReconnectAllowed())
                response.allow();

            if(!this.api.isWaited(player.getUniqueId()))
            {
                if (this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).onJoin(player, response);
            }
            else
            {
                this.api.getGame().playerReconnect(player);
            }
        }

        return response;
    }

    @Override
    public void onModerationJoin(Player player)
    {
        if(this.api.getGame() instanceof IMasterControlledGame)
            ((IMasterControlledGame) this.api.getGame()).onModerationJoin(player);
    }

    @Override
    public void onLogout(Player player)
    {
        this.api.onPlayerDisconnect(player);
    }
}
