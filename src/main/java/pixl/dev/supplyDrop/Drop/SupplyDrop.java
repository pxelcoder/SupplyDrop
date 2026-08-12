package pixl.dev.supplyDrop.Drop;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pixl.dev.supplyDrop.Loot.LootManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

public class SupplyDrop {
    private final Plugin plugin;
    private final Location location;
    private final MessageUtil messageUtil;
    private final NamespacedKey key;
    private final LootManager lootManager;

    public SupplyDrop(Plugin plugin, Location location, MessageUtil messageUtil, LootManager lootManager, NamespacedKey key) {
        this.plugin = plugin;
        this.location= location;
        this.messageUtil = messageUtil;
        this.lootManager = lootManager;
        this.key = key;
    }
    public void startDrop(){
        startDropAnimation();
        String msg = messageUtil.getMessage("messages.incoming", location);
        Bukkit.broadcastMessage(msg);
    }
    private void startDropAnimation(){
        int height = getHeightAbove();

        Location startLocation = location.clone().add(0,height,0);

        placeSupplyDrop(startLocation);

        int ticksperblock = 5;

        new BukkitRunnable(){
            private final Location currentLocation = startLocation.clone();


            // Brings a blockdisplay item down to the location, removes it, and drops a real chest there instead.
            @Override
            public void run(){
                if (currentLocation.getY()<=location.getY()){ // cancels if reached location
                    finalSpawnParticles(currentLocation);
                    playLandSound(currentLocation);
                    cancel();
                    return;
                }
                // moves chest down
                currentLocation.getBlock().setType(Material.AIR);
                currentLocation.subtract(0,1,0);
                placeSupplyDrop(currentLocation);
                spawnParticles(currentLocation);
                playSound(currentLocation);
            }
        }.runTaskTimer(plugin,0L,ticksperblock);
    }
    private void placeSupplyDrop(Location location){
        location.getBlock().setType(Material.CHEST);
        Chest chest = (Chest) location.getBlock().getState();
        chest.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        chest.update();
    }
    public void putLoot(){
        lootManager.dropLoot(location);
        location.getBlock().setType(Material.AIR);
        playLandSound(location);
    }
    public void stopDrop(){
        if (location.getBlock().getState() instanceof Chest){
            location.getBlock().setType(Material.AIR);
            finalSpawnParticles(location);
        }
    }
    public NamespacedKey getKey(){
        if (key == null){
            return null;
        }
        return key;
    }
    private void spawnParticles(Location location){
        String particleName = getParticle();
        try {
            Particle particle = Particle.valueOf(particleName);
            location.getWorld().spawnParticle(particle,location.clone().add(0.5,0.5,0.5),10,0.25,0.25,0.25,0.02);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid Particle Name: " + particleName);
        }
    }
    public void finalSpawnParticles(Location location){ // spawns a circle around location
        String particleName = getLandParticle();
        try {
            Particle particle = Particle.valueOf(particleName);
            double radius = 2.0;
            int numofparticles = 40;
            Location center = location.clone().add(0.5,0.1,0.5);
            for (int i=0; i<numofparticles; i++){
                double angle = 2*Math.PI*i/numofparticles;
                double x = Math.cos(angle)*radius;
                double z = Math.sin(angle)*radius;
                Location particleLocation = center.clone().add(x,0,z);
                location.getWorld().spawnParticle(particle,particleLocation,1,0,0,0,0);
            }
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid Particle Name: " + particleName);
        }
    }
    private void playSound(Location location){
        location.getWorld().playSound(location,Sound.BLOCK_NOTE_BLOCK_HAT,2.0f,1.0f);
    }
    private void playLandSound(Location location){
        String soundName = getLandingSound();
        Sound sound;
        location.getWorld().playSound(location,Sound.BLOCK_ANVIL_LAND,3.0f,1.0f);
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
    private String getLandParticle(){
        return plugin.getConfig().getString("effects.landing_effect");
    }
    private String getLandingSound(){
        return plugin.getConfig().getString("effects.landing_sound");
    }
}
