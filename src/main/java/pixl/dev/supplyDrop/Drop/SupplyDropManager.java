package pixl.dev.supplyDrop.Drop;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pixl.dev.supplyDrop.Loot.LootManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public class SupplyDropManager {
    private final Plugin plugin;
    private final DropLocation dropLocation;
    private final MessageUtil messageUtil;
    private final LootManager lootManager;
    private final NamespacedKey key;

    private SupplyDrop activeDrop;

    public SupplyDropManager(Plugin plugin, DropLocation dropLocation, MessageUtil messageUtil, LootManager lootManager) {
        this.plugin = plugin;
        this.dropLocation = dropLocation;
        this.messageUtil = messageUtil;
        this.lootManager = lootManager;
        this.key = new NamespacedKey(plugin, "supplyDrop");
    }

    public void startDrop(){
        if (activeDrop!=null){
            return;
        }
        Location currentDrop = dropLocation.getDropLocation();
        if (currentDrop==null){
            return;
        }
        activeDrop = new SupplyDrop(plugin,currentDrop,messageUtil, lootManager, key);
        activeDrop.startDrop();
    }
    public void stopDrop(){
        // need to call the supplydrop here to tell it to do the loot
        activeDrop.putLoot();
        activeDrop.stopDrop();
        activeDrop = null;
    }
    public void adminStopDrop(){
        activeDrop.stopDrop();
        activeDrop = null;
    }
    public SupplyDrop getActiveDrop(){
        return activeDrop;
    }
    public boolean isDropActive(){
        return activeDrop!=null;
    }
    public NamespacedKey getDropKey(){
        return key;
    }
    public Location getDropLocation(){
        return dropLocation.getDropLocation();
    }
    public void startScheduledSupplyDrops(){
        if (!(plugin.getConfig().getBoolean("settings.automatic_drops"))){
            return;
        }

        long time = plugin.getConfig().getLong("settings.time");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (activeDrop!=null){
                    stopDrop();
                }
                startDrop();
            }
        }.runTaskTimer(plugin,time,time);
    }
}
