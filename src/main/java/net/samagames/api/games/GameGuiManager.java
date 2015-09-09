package net.samagames.api.games;

import net.samagames.api.gui.AbstractGui;
import net.samagames.api.gui.IGuiManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameGuiManager implements IGuiManager
{
    private ConcurrentHashMap<UUID, AbstractGui> playersGui = new ConcurrentHashMap<>();

    public void openGui(Player player, AbstractGui gui)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
        {
            player.closeInventory();
            this.playersGui.remove(player.getUniqueId());
        }

        this.playersGui.put(player.getUniqueId(), gui);
        gui.display(player);
    }

    public void closeGui(Player player)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
        {
            player.closeInventory();
            this.playersGui.remove(player.getUniqueId());
        }
    }

    public void removeClosedGui(Player player)
    {
        if(this.playersGui.containsKey(player.getUniqueId()))
        {
            this.playersGui.remove(player.getUniqueId());
        }
    }

    @Override
    public AbstractGui getPlayerGui(HumanEntity player)
    {
        return this.getPlayerGui(player.getUniqueId());
    }

    public AbstractGui getPlayerGui(UUID uuid)
    {
        if(this.playersGui.containsKey(uuid))
        {
            return this.playersGui.get(uuid);
        }
        else
        {
            return null;
        }
    }

    @Override
    public ConcurrentHashMap<UUID, AbstractGui> getPlayersGui()
    {
        return this.playersGui;
    }
}
