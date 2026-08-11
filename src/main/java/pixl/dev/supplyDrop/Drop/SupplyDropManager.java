package pixl.dev.supplyDrop.Drop;

import org.bukkit.plugin.Plugin;

public class SupplyDropManager {
    private final Plugin plugin;
    private boolean dropRunning = false;

    public SupplyDropManager(Plugin plugin){
        this.plugin = plugin;
    }

    public void startDrop(){
        dropRunning = true;
    }
    public boolean isStillDrop(){
        return true;
    }
    public void stopDrop(){
        dropRunning = false;
    }
    public void removeDrop(){

    }
}
