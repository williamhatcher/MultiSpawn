package co.linkpub.multispawn;

import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.Objects;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Plugin(name = "MultiSpawn", version = "1.0.1")
@Author("William 'psyFi' Hatcher (William_CTO)")
@Website("https://hatcher.work/multispawn"
public class MultiSpawn extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("addSpawn")).setExecutor(new CommandAddSpawn());
        Objects.requireNonNull(this.getCommand("nearestSpawn")).setExecutor(new CommandNearestSpawn());
        Objects.requireNonNull(this.getCommand("listSpawns")).setExecutor(new CommandListSpawns());
        Objects.requireNonNull(this.getCommand("realSpawn")).setExecutor(new CommandRealSpawn());

        // bStats
        Metrics metrics = new Metrics(this);
        getLogger().info("MultiSpawn Enabled");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (this.getConfig().getStringList("excluded_worlds").contains(player.getWorld().getName())) return;
        Location loc = Spawns.getInstance().closest(player.getLocation());
        event.setRespawnLocation(loc);
    }
}
