package pixl.dev.supplyDrop.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

public class MessageUtil {
    // A handler for the config messages to keep it from getting too messy in the main managers

    private final Plugin plugin;

    public MessageUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String message) {
        String msg = plugin.getConfig().getString(message);

        if (msg == null) {
            return "";
        }
        // Automatically adds the prefix to the message that is returned
        return color(plugin.getConfig().getString("messages.prefix")) + color(msg);
    }
    public String getMessage(String message, Location location){
        String msg = plugin.getConfig().getString(message);

        if (msg == null) {
            return "";
        }
        // Replaces placeholders
        msg = msg
                .replace("%x%", String.valueOf(location.getBlockX()))
                .replace("%y%", String.valueOf(location.getBlockY()))
                .replace("%z%", String.valueOf(location.getBlockZ()));

        // Automatically adds the prefix to the message that is returned
        return color(plugin.getConfig().getString("messages.prefix")) + color(msg);
    }
    public String getPrefix() {
        String msg = color(plugin.getConfig().getString("messages.prefix"));
        if (msg == null) {
            return "";
        }
        return msg;
    }
    public String getOpeningMessage(HumanEntity humanEntity) {
        String msg = getMessage("messages.opened");
        msg = msg.replace("%player%", humanEntity.getName());
        if (msg == null) {
            return "";
        }
        return msg;
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}