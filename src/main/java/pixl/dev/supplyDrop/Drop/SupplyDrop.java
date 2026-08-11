package pixl.dev.supplyDrop.Drop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
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
        messageUtil.getMessage("messages.incoming");
    }
    private void startDropAnimation(Location location){
        int speed = getSpeed();
        int height = getHeightAbove();

        Location startLocation = location.clone().add(0,height,0);

        FallingBlock chest = location.getWorld().spawnFallingBlock(startLocation,Material.CHEST.createBlockData());
        chest.setGravity(false);
        chest.setDropItem(false);

        double blocksPerTick = speed/20.0;

        new BukkitRunnable(){
            private final Location currentLocation = startLocation.clone();

            // Brings a fallingblock item down to the location, removes it, and drops a real chest there instead.
            @Override
            public void run(){
                if (currentLocation.getY()<=location.getY()){
                    chest.remove();
                    dropChest(location);
                    cancel();
                    return;
                }
                currentLocation.subtract(0,blocksPerTick,0);
                chest.teleport(currentLocation);
            }
        }.runTaskTimer(plugin,0L,1L);

    }

    private void dropChest(Location location){
        location.getBlock().setType(Material.CHEST);
    }
    private void putLoot(Location location){
        return;
    }

    // CONFIG READING //

    private int getSpeed(){
        return plugin.getConfig().getInt("falling_crate.speed");
    }
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
