package net.samagames.api.games;

import net.samagames.api.network.JoinHandler;
import net.samagames.api.network.JoinResponse;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface IMasterControlledGame extends JoinHandler
{
    @Override
    public JoinResponse onLogin(UUID player, JoinResponse response);

    @Override
    public JoinResponse onJoin(Player player, JoinResponse response);

    @Override
    public void onModerationJoin(Player player);

    @Override
    public void onLogout(Player player);

    @Override
    public JoinResponse onPreJoinParty(Set<UUID> players, JoinResponse response);
}
