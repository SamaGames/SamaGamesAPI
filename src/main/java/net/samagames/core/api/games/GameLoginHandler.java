package net.samagames.core.api.games;

import net.samagames.api.games.*;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import net.samagames.api.network.ResponseType;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class GameLoginHandler implements JoinHandler
{
    private final GameManager api;

    public GameLoginHandler(GameManager api)
    {
        this.api = api;
    }

    @Override
    public void finishJoin(Player player)
    {
        if (this.api.getGame() != null)
        {
            if(!this.api.isWaited(player.getUniqueId()))
            {
                if (this.api.getGame() instanceof IMasterControlledGame)
                    this.api.getGame().playerJoin(player);
            }
            else
            {
                if(this.api.getGame() instanceof IReconnectGame)
                    ((IReconnectGame) this.api.getGame()).playerReconnect(player);
            }
        }
    }

    @Override
    public JoinResponse requestJoin(UUID player, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed())
            {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).requestJoin(player, response);
                else
                    return response;
            }

            IManagedGame game = this.api.getGame();

            if (game.getStatus() == Status.IN_GAME)
                response.disallow(ResponseType.DENY_IN_GAME);
            else if (game.getStatus() == Status.STARTING)
                response.disallow(ResponseType.DENY_NOT_READY);
            else if (game.getConnectedPlayers() >= game.getMaxPlayers())
                response.disallow(ResponseType.DENY_FULL);

            if(this.api.isReconnectAllowed())
                response.allow();

            if(!this.api.isWaited(player))
            {
                if (this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).requestJoin(player, response);
            }
        }
        return response;
    }

    @Override
    public JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed())
            {
                if(this.api.getGame() instanceof IMasterControlledGame)
                   return ((IMasterControlledGame) this.api.getGame()).requestPartyJoin(partyLeader, partyMembers, response);
            }
            else
            {
                IManagedGame game = this.api.getGame();

                if (game.getStatus() == Status.IN_GAME)
                    response.disallow(ResponseType.DENY_IN_GAME);
                else if (game.getStatus() == Status.STARTING)
                    response.disallow(ResponseType.DENY_NOT_READY);
                else if ((game.getConnectedPlayers() + partyMembers.size()) > game.getMaxPlayers())
                    response.disallow(ResponseType.DENY_FULL);

                if (this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).requestPartyJoin(partyLeader, partyMembers, response);
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
