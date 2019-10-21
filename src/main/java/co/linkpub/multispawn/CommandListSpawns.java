package co.linkpub.multispawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

import java.util.List;
import java.util.Set;

@Commands(@Command(name = "listSpawns", desc = "List all spawnpoints", usage = "/<command> <x y z>",
        permission = "multispawn.list", permissionMessage = "You are not allowed to list spawnpoints"))
@Permission(name = "multispawn.list", desc = "Allows listing of spawnpoints", defaultValue = PermissionDefault.OP)
class CommandListSpawns implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] strings) {
        Set<String> worlds = Spawns.getInstance().getWorlds();
        if (worlds.size() == 0) {
            sender.sendMessage(ChatColor.YELLOW + "No spawnpoints configured. Add one using "
                    + ChatColor.RED + "/addspawn");
            return true;
        }
        for (String world : worlds) {
            List<Location> locationList = Spawns.getInstance().worldSpawns(world);
            if (locationList == null) continue;
            sender.sendMessage(ChatColor.UNDERLINE + world);
            int i = 1;
            for (Location l : locationList) {
                sender.sendMessage(String.format(" %d - X: %d Y: %d Z: %d",
                        i, l.getBlockX(), l.getBlockY(), l.getBlockZ()));
                i++;
            }
        }
        return true;
    }
}
