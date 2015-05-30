package net.samagames.api.games;

import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public abstract class IMasterControlledGame extends IManagedGame implements JoinHandler
{
    @Override
    public abstract  JoinResponse requestJoin(UUID player, JoinResponse response);

    @Override
    public abstract JoinResponse requestPartyJoin(UUID partyLeader, Set<UUID> partyMembers, JoinResponse response);

    @Override
    public abstract void onModerationJoin(Player player);
}
