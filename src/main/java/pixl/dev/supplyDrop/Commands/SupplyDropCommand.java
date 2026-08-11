package pixl.dev.supplyDrop.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SupplyDropCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("supplydrop")) {
            // Normal player permission command chain
            if (p.hasPermission("supplydrop.player")) {

            }
            // Admin permission command chain
            if (p.hasPermission("supplydrop.admin")) {

            }
        }

        return true;
    }
    // Autocompleter for commands
    public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1) {

        }
        if (args.length == 2) {

        }
        return completions;
    }
}
