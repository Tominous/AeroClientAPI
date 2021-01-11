package net.aeroclient.aeroclientapi;

import net.aeroclient.aeroclientapi.command.*;

import net.aeroclient.aeroclientapi.manager.*;
import net.aeroclient.aeroclientapi.utils.hcfCores.HCFFallback;
import net.aeroclient.aeroclientapi.utils.hcfCores.HCRealms;
import net.aeroclient.aeroclientapi.utils.hcfCores.HCTeams;
import net.aeroclient.aeroclientapi.utils.hcfCores.Lazarus;
import net.aeroclient.aeroclientapi.utils.nms.v1_7_R4;
import lombok.Getter;
import net.aeroclient.aeroclientapi.listeners.ClientLoginListener;
import net.aeroclient.aeroclientapi.utils.nms.NMSHandler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AeroClientAPI extends JavaPlugin {
    @Getter private static AeroClientAPI instance;
    @Getter private static PlayerManager playerManager;
    @Getter private static NMSHandler nmsHandler;
    @Getter private static BanWaveManager banWaveManager;
    @Getter private static AutoUpdateManager autoUpdateManager;
    @Getter private static ConfigManager configManager;
    @Getter private static CustomNameTagsManager customNameTagsManager;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        banWaveManager = new BanWaveManager();
        autoUpdateManager = new AutoUpdateManager();
        configManager = new ConfigManager();
        customNameTagsManager = new CustomNameTagsManager();
        Bukkit.getPluginManager().registerEvents(new ClientLoginListener(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "AC-Client"); // Aero Client Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Lunar-Client"); // Lunar Client Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "CB-Client"); // CheatBreaker Client Channel
        configManager.load();
        getCommand("clientlist").setExecutor(new ClientListCommand());
        getCommand("apiinfo").setExecutor(new APIInfoCommand());
        getCommand("apihelp").setExecutor(new APIHelpCommand());
        getCommand("client").setExecutor(new AeroClientCommand());
        getCommand("clientban").setExecutor(new ClientBanCommand());
        getCommand("clientunban").setExecutor(new ClientUnbanCommand());
        getCommand("staffmods").setExecutor(new StaffModsCommand());
        getCommand("banwave").setExecutor(new BanwaveCommand());
        getCommand("apireload").setExecutor(new APIReload());
        getCommand("setwaypoint").setExecutor(new SetWaypoint());
        getCommand("anotification").setExecutor(new ANotification());
        String name = getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        nmsHandler = new v1_7_R4();
        if (getServer().getPluginManager().getPlugin("HCTeams") != null)
            getCustomNameTagsManager().hcfCore = new HCTeams();
        else if (getServer().getPluginManager().getPlugin("Lazarus") != null)
            getCustomNameTagsManager().hcfCore = new Lazarus();
        else if (getServer().getPluginManager().getPlugin("HCRealms") != null)
            getCustomNameTagsManager().hcfCore = new HCRealms();
        else {
            getCustomNameTagsManager().hcfCore = new HCFFallback();
            getLogger().info("Disabling custom name tags with faction placeholders due to a supported plugin not being installed. Please install HCTeams or Lazarus to use this feature");
        }
        getCustomNameTagsManager().run();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                autoUpdateManager.checkForUpdate();
            } catch (IOException e) {
                AeroClientAPI.getInstance().getLogger().info("Looks to be that github api is down, couldn't check for updates.");
            }
        }, 100);
    }
}
