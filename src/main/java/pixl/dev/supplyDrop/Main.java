package pixl.dev.supplyDrop;

import org.bukkit.plugin.java.JavaPlugin;
import pixl.dev.supplyDrop.Commands.SupplyDropCommand;
import pixl.dev.supplyDrop.Drop.DropLocation;
import pixl.dev.supplyDrop.Drop.SupplyDrop;
import pixl.dev.supplyDrop.Drop.SupplyDropManager;
import pixl.dev.supplyDrop.Listeners.ClaimListener;
import pixl.dev.supplyDrop.Loot.LootManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public final class Main extends JavaPlugin {

    private SupplyDropManager supplyDropManager;
    private DropLocation dropLocation;
    private MessageUtil messageUtil;
    private LootManager lootManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getLogger().info("SupplyDrop has been enabled!");

        // utils and managers
        messageUtil = new MessageUtil(this);
        lootManager = new LootManager(this);
        dropLocation = new DropLocation(this);

        supplyDropManager = new SupplyDropManager(this, dropLocation, messageUtil,  lootManager);
        supplyDropManager.startScheduledSupplyDrops();

        // listeners
        getServer().getPluginManager().registerEvents(new ClaimListener(supplyDropManager,(supplyDropManager.getDropKey()),messageUtil),this);

        SupplyDropCommand sdc = new SupplyDropCommand(this,messageUtil,supplyDropManager);
        getCommand("supplydrop").setExecutor(sdc); // command init
        getCommand("supplydrop").setTabCompleter(sdc); // command tab completer
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("SupplyDrop has been disabled!");
    }
}
