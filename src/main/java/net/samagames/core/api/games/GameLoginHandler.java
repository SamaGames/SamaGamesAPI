package net.samagames.core.api.games;

import net.samagames.api.SamaGamesAPI;
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
    public void finishJoin(Player player) {
        if (this.api.getGame() != null) {

            if (api.getReconnectHandler() != null && api.getReconnectHandler().canReconnect(player.getUniqueId())) {
                api.getReconnectHandler().reconnect(player);
            } else {
                this.api.getGame().playerJoin(player);
                if (api.getGame() instanceof IMasterControlledGame)
                    ((IMasterControlledGame) api.getGame()).finishJoin(player);
            }
        }
    }

    @Override
    public void onLogin(UUID player) {
        if (api.getGame() != null && api.getGame() instanceof IMasterControlledGame)
            ((IMasterControlledGame) api.getGame()).onLogin(player);
    }

    @Override
    public JoinResponse requestJoin(UUID player, JoinResponse response) {
        if (this.api.getGame() != null) {
            if (!response.isAllowed()) {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    return ((IMasterControlledGame) this.api.getGame()).requestJoin(player, response);
                else
                    return response;
            }

            IManagedGame game = this.api.getGame();

            if (game.getStatus() == Status.IN_GAME) {
                response.disallow(ResponseType.DENY_IN_GAME);
                // On check le rejoin
                if (api.getReconnectHandler() != null) {
                    if (api.getReconnectHandler().canReconnect(player))
                        response.allow();
                    else
                        response.disallow("Il est trop tard pour se reconnecter.");
                }
            } else if (game.getStatus() == Status.STARTING)
                response.disallow(ResponseType.DENY_NOT_READY);
            else if (game.getConnectedPlayers() > game.getTotalMaxPlayers() && ! SamaGamesAPI.get().getPermissionsManager().hasPermission(player, "games.joinfull"))
                response.disallow(ResponseType.DENY_FULL);
            else if (game.getConnectedPlayers() > game.getMaxPlayers() && !SamaGamesAPI.get().getPermissionsManager().hasPermission(player, "games.joinvip"))
                response.disallow(ResponseType.DENY_VIPONLY);

            if (this.api.getGame() instanceof IMasterControlledGame)
                return ((IMasterControlledGame) this.api.getGame()).requestJoin(player, response);
        }
        return response;
    }

    @Override
    public JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response) {
        if (this.api.getGame() != null)
        {
            if (!response.isAllowed())
            {
                if(this.api.getGame() instanceof IMasterControlledGame)
                    response = ((IMasterControlledGame) this.api.getGame()).requestPartyJoin(partyLeader, partyMembers, response);
            }
            else
            {
                IManagedGame game = this.api.getGame();

                if (game.getStatus() == Status.IN_GAME)
                    response.disallow(ResponseType.DENY_IN_GAME);
                else if (game.getStatus() == Status.STARTING)
                    response.disallow(ResponseType.DENY_NOT_READY);
                else if (game.getConnectedPlayers() + partyMembers.size() > game.getTotalMaxPlayers())
                    response.disallow(ResponseType.DENY_FULL);
                else if (game.getConnectedPlayers() + partyMembers.size() > game.getMaxPlayers() && ! SamaGamesAPI.get().getPermissionsManager().hasPermission(partyLeader, "games.joinvip"))
                    response.disallow(ResponseType.DENY_VIPONLY);

                if (this.api.getGame() instanceof IMasterControlledGame)
                    response = ((IMasterControlledGame) this.api.getGame()).requestPartyJoin(partyLeader, partyMembers, response);
            }
        }

        return response;
    }

    @Override
    public void onModerationJoin(Player player) {
        if(this.api.getGame() instanceof IMasterControlledGame)
            ((IMasterControlledGame) this.api.getGame()).onModerationJoin(player);
    }

    @Override
    public void onLogout(Player player) {
        if (api.getReconnectHandler() != null)
            api.getReconnectHandler().disconnect(player);
    }
}
