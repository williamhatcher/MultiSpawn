package co.linkpub.multispawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Spawns {
    private static Spawns single_instance = null;
    private final String filename = "spawns.yml";
    private final File configFile = new File("plugins" + File.separator + "MultiSpawn" + File.separator + filename);
    private final FileConfiguration spawnsConfig = YamlConfiguration.loadConfiguration(configFile);

    // Private Constructor
    private Spawns() {
        try {
            if (!configFile.exists()) {
                Bukkit.getLogger().info("Creating spawns.yml");
                if (!configFile.getParentFile().mkdirs()) throw new IOException();
                if (!configFile.createNewFile()) throw new IOException();
                World w = getDefaultWorld();
                List<Location> loc = new ArrayList<>();
                // get default spawn location
                loc.add(w.getSpawnLocation());
                spawnsConfig.set(w.getName(), loc);
                spawnsConfig.save(configFile)
            }
        } catch (IOException e) {
            Bukkit.getLogger().severe("Unable to create spawns.yml! Please manually create it.");
        }
    }

    static World getDefaultWorld() throws IOException {
        BufferedReader is = new BufferedReader(new FileReader("server.properties"));
        Properties prop = new Properties();
        prop.load(is);
        return Bukkit.getWorld(prop.getProperty("level-name"));
    }

    // Public instance
    static Spawns getInstance() {
        if (single_instance == null) {
            single_instance = new Spawns();
        }
        return single_instance;
    }

    private DL distance(Location loc, Location playerLocation) {
        return new DL(playerLocation.distance(loc), loc);
    }

    List<Location> worldSpawns(String world) {
        if (world == null) return null;
        //noinspection unchecked
        return (List<Location>) spawnsConfig.getList(world);
    }

    boolean addSpawn(Location location) {
        List<Location> wl = worldSpawns(Objects.requireNonNull(location.getWorld()).getName());
        if (wl == null) {
            wl = new ArrayList<>();
            spawnsConfig.set(location.getWorld().getName(), wl);
        }
        wl.add(location);
        return saveConfig();
    }

    Location closest(Location playerLocation) throws NullPointerException {
        List<Location> worldLocations = worldSpawns(Objects.requireNonNull(playerLocation.getWorld()).getName());

        if (worldLocations == null) return null;
        Optional<DL> closest = worldLocations.stream().map(loc -> distance(loc, playerLocation)).sorted().findFirst();
        if (closest.isPresent()) {
            return closest.get().location;
        }
        throw new NullPointerException();
    }

    Set<String> getWorlds() {
        return spawnsConfig.getKeys(false);
    }

    private boolean saveConfig() {
        try {
            spawnsConfig.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Unable to save " + filename);
            return false;
        }
        return true;
    }

    private static class DL implements Comparable {
        final Double distance;
        final Location location;

        DL(Double distance, Location location) {
            this.distance = distance;
            this.location = location;
        }

        @Override
        public int compareTo(Object o) {
            return this.distance.compareTo(((DL) o).distance);
        }
    }
}
