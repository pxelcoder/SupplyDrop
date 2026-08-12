package pixl.dev.supplyDrop.Drop;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import pixl.dev.supplyDrop.Loot.LootManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public class SupplyDropManager {
    private final Plugin plugin;
    private final DropLocation dropLocation;
    private final MessageUtil messageUtil;
    private final LootManager lootManager;

    public SupplyDropManager(Plugin plugin, DropLocation dropLocation, MessageUtil messageUtil, LootManager lootManager) {
        this.plugin = plugin;
        this.dropLocation = dropLocation;
        this.messageUtil = messageUtil;
        this.lootManager = lootManager;
    }

    public void startDrop(){
        Location currentDrop = dropLocation.getDropLocation();
        SupplyDrop supdrop = new SupplyDrop(plugin, currentDrop, messageUtil);
        supdrop.startDrop(currentDrop);
        // Something about lootmanager here, like lootManager.populateLoot(Location currentDrop);
        // ignore and put it in the listener instead
    }
    public void stopDrop(){

    }
    public void removeDrop(){

    }
}
