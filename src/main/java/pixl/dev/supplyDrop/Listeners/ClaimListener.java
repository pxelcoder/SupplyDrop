package pixl.dev.supplyDrop.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataType;
import pixl.dev.supplyDrop.Drop.SupplyDropManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public class ClaimListener implements Listener {
    private final SupplyDropManager supplyDropManager;
    private final NamespacedKey key;
    private final MessageUtil messageUtil;

    public ClaimListener(SupplyDropManager supplyDropManager,  NamespacedKey key, MessageUtil messageUtil) {
        this.supplyDropManager = supplyDropManager;
        this.key = key;
        this.messageUtil = messageUtil;
    }

    // InventoryOpenEvent
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getInventory().getHolder() instanceof Chest chest)){
            return;
        }
        // is it a supplydrop chest?
        if (!chest.getPersistentDataContainer().has(key, PersistentDataType.BYTE)){
            return;
        }
        supplyDropManager.stopDrop();
        Bukkit.broadcastMessage(messageUtil.getOpeningMessage(event.getPlayer()));
        event.setCancelled(true);
    }
}
