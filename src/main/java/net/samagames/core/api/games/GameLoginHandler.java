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

import java.util.UUID;

public class GameLoginHandler implements JoinHandler
{
    private final GameManager api;

    public GameLoginHandler(GameManager api)
    {
        this.api = api;
    }

    @Override
    public JoinResponse onLogin(UUID player, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed())
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
                response.allow();

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
			else if (info.getConnectedPlayers() > info.getMaxPlayers() && !PermissionsBukkit.hasPermission(player, "games.joinvip"))
				response.disallow(ResponseType.DENY_VIPONLY);

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
