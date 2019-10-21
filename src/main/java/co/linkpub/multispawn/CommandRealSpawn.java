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

import java.io.IOException;

@Commands(@Command(name = "realSpawn", usage = "/<command> [player]", desc = "Teleport you to the true world spawn",
        permission = "multispawn.realspawn",
        permissionMessage = "This command is disabled. See console to re-enable it"))
@Permission(name = "multispawn.realspawn", desc = "Allows use of /realSpawn to teleport to true world spawn. Useful for debugging",
        defaultValue = PermissionDefault.FALSE)
@Permission(name = "multispawn.realspawn.others", defaultValue = PermissionDefault.FALSE,
        desc = "Allows use of /realSpawn to teleport others to true world spawn.")
class CommandRealSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (args.length > 0) {
            if (!sender.hasPermission("multispawn.realspawn.others")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
                return true;
            }
            Location loc;
            try {
                loc = Spawns.getDefaultWorld().getSpawnLocation();
            } catch (IOException e) {
                sender.sendMessage(ChatColor.DARK_RED + "Internal error occurred; unable to teleport to true spawn. " +
                        "Please disable the plugin and contact William_CTO");
                return true;
            }
            for (String a : args) {
                Player p = Bukkit.getPlayer(a);
                if (p == null) continue;
                p.teleport(loc);
            }
            return true;
        }

        if (!(sender instanceof Player)) return false;
        try {
            ((Player) sender).teleport(Spawns.getDefaultWorld().getSpawnLocation());
        } catch (IOException e) {
            sender.sendMessage(ChatColor.DARK_RED + "Internal error occurred; unable to teleport you to true spawn. " +
                    "Please disable the plugin and contact William_CTO");
        }
        return true;
    }
}
