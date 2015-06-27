package net.samagames.core.api.games;

import net.samagames.api.games.IGameManager;
import net.samagames.api.games.Status;
import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import net.samagames.api.network.ResponseType;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class GameLoginHandler implements JoinHandler
{
    private final IGameManager api;

    public GameLoginHandler(IGameManager api)
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
                this.api.getGame().handleLogin(player);
            }
            else
            {
                this.api.getGame().handleReconnect(player);
            }

            this.api.refreshArena();
        }
    }

    @Override
    public JoinResponse requestJoin(UUID player, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            AbstractGame game = this.api.getGame();

            if (game.getStatus() == Status.IN_GAME)
                response.disallow(ResponseType.DENY_IN_GAME);
            else if (game.getStatus() == Status.STARTING)
                response.disallow(ResponseType.DENY_NOT_READY);
            else if (game.getConnectedPlayers() >= this.api.getGameProperties().getMaxSlots())
                response.disallow(ResponseType.DENY_FULL);

            if(this.api.isReconnectAllowed() && this.api.isWaited(player))
            {
                response.allow();
                return response;
            }

            Pair<Boolean, String> gameResponse = game.canJoinGame(false);

            if(gameResponse.getKey())
            {
                response.allow();
            }
            else
            {
                response.disallow(gameResponse.getValue());
            }
        }

        return response;
    }

    @Override
    public JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response)
    {
        if (this.api.getGame() != null)
        {
            AbstractGame game = this.api.getGame();

            if (game.getStatus() == Status.IN_GAME)
                response.disallow(ResponseType.DENY_IN_GAME);
            else if (game.getStatus() == Status.STARTING)
                response.disallow(ResponseType.DENY_NOT_READY);
            else if (game.getConnectedPlayers() >= this.api.getGameProperties().getMaxSlots())
                response.disallow(ResponseType.DENY_FULL);

            Pair<Boolean, String> gameResponse = game.canJoinGame(true);

            if(gameResponse.getKey())
            {
                response.allow();
            }
            else
            {
                response.disallow(gameResponse.getValue());
            }
        }

        return response;
    }

    @Override
    public void onModerationJoin(Player player)
    {
        this.api.getGame().handleModeratorLogin(player);
    }

    @Override
    public void onLogout(Player player)
    {
        this.api.onPlayerDisconnect(player);
    }
}
