package pixl.dev.supplyDrop;

import org.bukkit.plugin.java.JavaPlugin;
import pixl.dev.supplyDrop.Drop.SupplyDrop;
import pixl.dev.supplyDrop.Drop.SupplyDropManager;

public final class Main extends JavaPlugin {

    private SupplyDropManager supplyDropManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getLogger().info("SupplyDrop has been enabled!");

        supplyDropManager = new SupplyDropManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("SupplyDrop has been disabled!");
    }
}
