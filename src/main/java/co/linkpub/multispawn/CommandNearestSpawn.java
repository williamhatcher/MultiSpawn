package co.linkpub.multispawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

import java.util.Objects;

@Commands(@Command(name = "nearestSpawn", permission = "multispawn.nearest", permissionMessage = "You cannot do that",
        desc = "Teleports player to nearest spawnpoint", usage = "/<command> [player]"))
@Permission(name = "multispawn.nearest", desc = "Allows use of /nearestSpawn to teleport to nearest spawnpoint",
        defaultValue = PermissionDefault.OP)
@Permission(name = "multispawn.nearest.others", defaultValue = PermissionDefault.OP,
        desc = "Allows use of /nearestSpawn to teleport others to their nearest spawnpoint")
class CommandNearestSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length == 1) {
            if (!sender.hasPermission("multispawn.nearest.others")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                return true;
            }
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(ChatColor.RED + args[0] + " is not a valid player");
                return true;
            }
            Location loc = Spawns.getInstance().closest(p.getLocation());
            p.teleport(loc);
            sender.sendMessage(ChatColor.GREEN + "Teleported " + args[0]
                    + String.format(" to (%d, %d, %d)", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
            return true;
        }

        // Only players are able to teleport to nearest spawn
        if (!(sender instanceof Player)) return false;
        Location loc = Spawns.getInstance().closest(((Player) sender).getLocation());
        sender.sendMessage(String.format("Closest spawnpoint: X: %d Y: %d Z: %d in %s",
                loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), Objects.requireNonNull(loc.getWorld()).getName()));
        ((Player) sender).teleport(loc);
        return true;
    }
}
