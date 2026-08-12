package pixl.dev.supplyDrop.Loot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LootManager {

// Reads the config and spawns in some loot for it, to be called by other things like supplydropmanager. Potentially
    // put in a test command in supplydropcommand for it.

    private final Plugin plugin;
    public LootManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void dropLoot(Location location) {
        ConfigurationSection configsection = plugin.getConfig().getConfigurationSection("loot_table.items");

        if (configsection==null){
            return;
        }

        List<String> items = new ArrayList<>(configsection.getKeys(false));

        int min = plugin.getConfig().getInt("loot.min_items",1);
        int max = plugin.getConfig().getInt("loot.max_items",3);

        int amount = ThreadLocalRandom.current().nextInt(min,max+1);

            for (int i=0;i<amount;i++) {
                String itemName = items.get(ThreadLocalRandom.current().nextInt(0,items.size()));
                dropItem(location,itemName);
            }
        }
    private void dropItem(Location location, String itemName) {
        String material = plugin.getConfig().getString("loot_table.items." + itemName + ".material");

        Material mat = Material.matchMaterial(material);

        if (mat == null) {
            plugin.getLogger().warning("Material not found: " + material);
            return;
        }
        int minamount = plugin.getConfig().getInt("loot.min_amount",1);
        int maxamount = plugin.getConfig().getInt("loot.max_amount",3);

        int amount = ThreadLocalRandom.current().nextInt(minamount,maxamount+1);

        ItemStack item = new ItemStack(mat,amount);
        location.getWorld().dropItemNaturally(location,item);
    }
}
