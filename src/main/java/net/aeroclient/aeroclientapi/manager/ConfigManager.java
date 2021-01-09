package net.aeroclient.aeroclientapi.manager;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import net.aeroclient.aeroclientapi.packets.PacketWaypointAdd;
import net.aeroclient.aeroclientapi.packets.PacketWaypointRemove;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {
    public FileConfiguration config;
    private File configFile;
    public ArrayList<PacketWaypointAdd> waypoints = new ArrayList<>();
    public boolean customNametag = false;
    public boolean aeroClientOnly = false;
    public String aeroClientOnlyMessage = "";
    public ArrayList<String> nameTag = new ArrayList<>();


    public void getWaypointsFC() {
        if(!waypoints.isEmpty()) {
            for(PacketWaypointAdd waypointsAdd : waypoints) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    new PacketWaypointRemove(waypointsAdd.getWorld(), waypointsAdd.getName()).setTo(player).sendPacket();
                }
            }
            waypoints.clear();

        }
        for(String waypoint : config.getConfigurationSection("waypoints").getKeys(false)) {
            ConfigurationSection configurationSection = config.getConfigurationSection("waypoints." + waypoint);
            int x = configurationSection.getInt("location.x");
            int y = configurationSection.getInt("location.y");
            int z = configurationSection.getInt("location.z");
            String world  = configurationSection.getString("location.world");
            int color = configurationSection.getInt("color");
            String name = configurationSection.getString("name");
            PacketWaypointAdd packetWaypointAdd = new PacketWaypointAdd(name, world, color, x, y, z, true, true);
            waypoints.add(packetWaypointAdd);
        }

    }


    public void reload() {
        AeroClientAPI.getInstance().getLogger().info("Reloaded Config.yml");
        try {
            config.load(new File(AeroClientAPI.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        loadResources();
    }

    public void load() {
        configFile = new File(AeroClientAPI.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            AeroClientAPI.getInstance().getLogger().info("Creating Config.yml");
            configFile.getParentFile().mkdirs();
            AeroClientAPI.getInstance().saveResource("config.yml", false);
        }
        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
      loadResources();
    }

    private void loadResources() {
        Bukkit.getScheduler().runTaskLater(AeroClientAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                getWaypointsFC();
                customNametag = config.getBoolean("nametag.enabled");
                aeroClientOnly = config.getBoolean("settings.AeroClient-Only.enabled");
                aeroClientOnlyMessage = config.getString("settings.AeroClient-Only.message");
                if (customNametag) {
                    nameTag.add(config.getString("nametag.below"));
                    nameTag.add(config.getString("nametag.above"));
                }

            }
        }, 5L);
    }
}
