package net.samagames.api.games;

import net.samagames.api.network.JoinResponse;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IMasterControlledGame
{
    public JoinResponse onLogin(UUID player, JoinResponse response);
    public JoinResponse onJoin(Player player, JoinResponse response);
    public void onModerationJoin(Player player);
    public void onLogout(Player player);
}
