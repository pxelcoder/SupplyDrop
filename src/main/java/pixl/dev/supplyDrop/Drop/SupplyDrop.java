package pixl.dev.supplyDrop.Drop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public class SupplyDrop {
    private final Plugin plugin;
    private final Location location;
    private final MessageUtil messageUtil;

    public SupplyDrop(Plugin plugin, Location location, MessageUtil messageUtil){
        this.plugin = plugin;
        this.location= location;
        this.messageUtil = messageUtil;
    }
    public void startDrop(Location location){
        startDropAnimation(location);
        String msg = messageUtil.getMessage("messages.incoming", location);
        Bukkit.broadcastMessage(msg);
    }
    private void startDropAnimation(Location location){
        int height = getHeightAbove();

        Location startLocation = location.clone().add(0,height,0);

        startLocation.getBlock().setType(Material.CHEST);

        int ticksperblock = 15;

        new BukkitRunnable(){
            private final Location currentLocation = startLocation.clone();


            // Brings a blockdisplay item down to the location, removes it, and drops a real chest there instead.
            @Override
            public void run(){
                if (currentLocation.getY()<=location.getY()){ // cancels if reached location
                    cancel();
                    return;
                }
                // moves chest down
                currentLocation.getBlock().setType(Material.AIR);
                currentLocation.subtract(0,1,0);
                currentLocation.getBlock().setType(Material.CHEST);

            }
        }.runTaskTimer(plugin,0L,ticksperblock);
    }
    private void putLoot(Location location){
        return;
    }

    // CONFIG READING //

    private int getHeightAbove(){
        return plugin.getConfig().getInt("falling_crate.height");
    }
    private String getParticle(){
        return plugin.getConfig().getString("falling_crate.particle");
    }
    private String getSound(){
        return plugin.getConfig().getString("falling_crate.sound");
    }
}
