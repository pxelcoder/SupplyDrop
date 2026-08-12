package pixl.dev.supplyDrop.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pixl.dev.supplyDrop.Drop.SupplyDropManager;
import pixl.dev.supplyDrop.Utils.MessageUtil;

import java.util.ArrayList;

public class SupplyDropCommand implements CommandExecutor, TabCompleter {
    private final MessageUtil messageUtil;
    private final Plugin plugin;
    private final SupplyDropManager supplyDropManager;

    public SupplyDropCommand(Plugin plugin, MessageUtil messageUtil, SupplyDropManager supplyDropManager) {
        this.messageUtil = messageUtil;
        this.plugin = plugin;
        this.supplyDropManager = supplyDropManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("supplydrop")) {
            if (args.length == 0) {
                p.sendMessage(messageUtil.getPrefix() + "SupplyDrop is a plugin that creates crates of loot around the map.");
            }

            // Normal player permission command chain
            else {
                if (p.hasPermission("supplydrop.player")) {
                }
                // Admin permission command chain
                if (p.hasPermission("supplydrop.admin")) {
                    if (args[0].equalsIgnoreCase("start")) {
                        // add a check to see if a drop is active
                        supplyDropManager.startDrop();
                    }
                }
            }
        }

        return true;
    }
    // Autocompleter for commands
    public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("start");
        }
        if (args.length == 2) {

        }
        return completions;
    }
}
