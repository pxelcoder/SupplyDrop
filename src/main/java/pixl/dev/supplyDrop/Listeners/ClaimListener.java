package pixl.dev.supplyDrop.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import pixl.dev.supplyDrop.Drop.SupplyDropManager;

public class ClaimListener implements Listener {
    private final SupplyDropManager supplyDropManager;

    public ClaimListener(SupplyDropManager supplyDropManager) {
        this.supplyDropManager = supplyDropManager;
    }

    // InventoryOpenEvent
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

    // BlockBreakEvent
    public void onBlockBreak(BlockBreakEvent event) {

    }

}
