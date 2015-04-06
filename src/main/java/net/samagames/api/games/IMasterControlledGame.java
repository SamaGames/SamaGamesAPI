package net.samagames.api.games;

import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface IMasterControlledGame extends JoinHandler
{
    @Override
    JoinResponse requestJoin(UUID player, JoinResponse response);

    @Override
    JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response);

    @Override
    void onLogin(UUID player);

    @Override
    void finishJoin(Player player);

    @Override
    void onModerationJoin(Player player);

    @Override
    void onLogout(Player player);
}
