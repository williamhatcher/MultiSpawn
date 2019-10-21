package co.linkpub.multispawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

import java.io.IOException;
import java.util.Arrays;

@Commands(@Command(name = "addSpawn", permission = "multispawn.add", permissionMessage = "You are not allowed to add spawnpoints",
        desc = "Adds a new spawnpoint at your location", usage = "/<command> <x y z> [pitch] [yaw]"))
@Permission(name = "multispawn.add", desc = "Allows adding of spawnpoints", defaultValue = PermissionDefault.OP)
class CommandAddSpawn implements CommandExecutor {
    // Creates a spawn point where the player is standing or at the coords specified

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        Location loc = null;
        // Save location of player to config file
        if (sender instanceof Player) {
            Player p = (Player) sender;
            // use current coords
            loc = p.getLocation();
        } else if (sender instanceof ConsoleCommandSender) {
            // coords must be present
            if (args.length < 3) {
                return false;
            }
        } else {
            return false;
        }
        if (args.length >= 3) {
            try {
                // Convert string args to double
                double[] coords = Arrays.stream(args).mapToDouble(Double::parseDouble).toArray();
                // Get current player world or default world if sender is not player
                World world;
                if (sender instanceof Player) {
                    world = ((Player) sender).getWorld();
                } else {
                    world = Spawns.getDefaultWorld();
                }
                loc = new Location(world, coords[0], coords[1], coords[2]);
                // Set optional Yaw and Pitch
                if (coords.length >= 4) {
                    loc.setYaw((float) coords[3]);
                    if (coords.length == 5) {
                        loc.setPitch((float) coords[4]);
                    }
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.DARK_RED + "Unable to set spawnpoint for " + Arrays.toString(args));
                return false;
            } catch (IOException e) {
                sender.sendMessage(ChatColor.DARK_RED + "Unable to find default world.");
                return false;
            }
        }

        if (Spawns.getInstance().addSpawn(loc))
            sender.sendMessage(ChatColor.GREEN + String.format("Spawnpoint (%s %s %s) added!",
                    loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        else sender.sendMessage(ChatColor.RED + "Unable to add spawnpoint. Check console for more details.");
        return true;
    }
}
