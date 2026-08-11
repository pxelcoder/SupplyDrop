package pixl.dev.supplyDrop.Drop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import pixl.dev.supplyDrop.Main;

import java.util.concurrent.ThreadLocalRandom;

// Mainly utility, DropLocation chooses a random location to drop the SupplyDrop and ensures that it is a safe location.

public class DropLocation {

    private final Plugin plugin;

    public DropLocation(Plugin plugin) {
        this.plugin = plugin;
    }
    private int getMaxDistance(){
        int maxDistance = plugin.getConfig().getInt("spawn.max_distance");
        return maxDistance;
    }

    private boolean isDropLocationSafe(Location location){
        Material ground = location.clone().subtract(0,1,0).getBlock().getType();

        if (!ground.isSolid()){
            return false;
        }
        if (ground == Material.AIR||ground == Material.WATER||ground==Material.FIRE||ground==Material.SOUL_FIRE){
            return false;
        }
        if (location.getBlock().getType()==Material.AIR){
            return false;
        }

        // Should all these checks succeed, it's good to go!
        return true;
    }

    private Location getRandomDropLocation(World world){
        // Gets a random location. NOT FINAL. Doesn't check if it is safe (that is what getdroplocation is for)

        Location spawn = world.getSpawnLocation();
        int maxDistance = getMaxDistance();
        // Locally generated random number generator
        ThreadLocalRandom random = ThreadLocalRandom.current(); // Note to self, this is new to me so I might be misunderstanding it
        int x = random.nextInt(spawn.getBlockX()-maxDistance, spawn.getBlockX()+maxDistance);
        int z = random.nextInt(spawn.getBlockZ()-maxDistance, spawn.getBlockZ()+maxDistance);
        int y = world.getHighestBlockYAt(x, z);

        Location location = new Location(world, x, y, z);
        return location;
    }

    public Location getDropLocation(){
        World world =  Bukkit.getWorld(plugin.getConfig().getString("settings.world"));
        if (world == null){
            // This should not occur because it's fairly easy to type in a world name but you never know :/
            plugin.getLogger().warning("World not found!");
            return null;
        }

        for (int i=0; i<10; i++){
            Location loc = getRandomDropLocation(world);

            if (isDropLocationSafe(loc)){
                return loc;
            }
        }
        // If it exits the loop without finding a good drop location
        plugin.getLogger().warning("Could not find any drop location!");
        return null;
    }

}
