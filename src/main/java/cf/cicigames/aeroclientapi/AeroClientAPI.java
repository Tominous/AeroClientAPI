package cf.cicigames.aeroclientapi;

import cf.cicigames.aeroclientapi.command.*;
import cf.cicigames.aeroclientapi.manager.*;
import cf.cicigames.aeroclientapi.utils.hcfCores.HCFFallback;
import cf.cicigames.aeroclientapi.utils.hcfCores.HCRealms;
import cf.cicigames.aeroclientapi.utils.hcfCores.HCTeams;
import cf.cicigames.aeroclientapi.utils.hcfCores.Lazarus;
import cf.cicigames.aeroclientapi.utils.nms.NMSFallback;
import cf.cicigames.aeroclientapi.utils.nms.v1_7_R4;
import cf.cicigames.aeroclientapi.voicechat.PublicChannel;
import cf.cicigames.aeroclientapi.voicechat.VoiceChannelHandler;
import lombok.Getter;
import cf.cicigames.aeroclientapi.listeners.ClientLoginListener;
import cf.cicigames.aeroclientapi.utils.nms.NMSHandler;
import cf.cicigames.aeroclientapi.utils.nms.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AeroClientAPI extends JavaPlugin {
    @Getter private static AeroClientAPI instance;
    @Getter private static PlayerManager playerManager;
    @Getter private static NMSHandler nmsHandler;
    @Getter private static VoiceChannelHandler voiceChannelHandler;
    @Getter private static BanWaveManager banWaveManager;
    @Getter private static AutoUpdateManager autoUpdateManager;
    @Getter private static ConfigManager configManager;
    @Getter private static CustomNameTagsManager customNameTagsManager;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        voiceChannelHandler = new VoiceChannelHandler();
        banWaveManager = new BanWaveManager();
        autoUpdateManager = new AutoUpdateManager();
        configManager = new ConfigManager();
        customNameTagsManager = new CustomNameTagsManager();
        Bukkit.getPluginManager().registerEvents(new ClientLoginListener(), this);

        // Plugin startup logic
        getServer().getMessenger().registerOutgoingPluginChannel(this, "AC-Client");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Lunar-Client");
        configManager.load();
        getCommand("clients").setExecutor(new ClientUsersCommand());
        getCommand("APIinfo").setExecutor(new APIInfoCommand());
        getCommand("APIhelp").setExecutor(new APIHelpCommand());
        getCommand("client").setExecutor(new AeroClientCommand());
        getCommand("clientban").setExecutor(new ClientBanCommand());
        getCommand("clientunban").setExecutor(new ClientUnbanCommand());
        getCommand("staffmods").setExecutor(new StaffModsCommand());
        getCommand("banwave").setExecutor(new BanwaveCommand());
        getCommand("APIreload").setExecutor(new APIReload());
        getCommand("setwaypoint").setExecutor(new SetWaypoint());
        getCommand("anotification").setExecutor(new ANotification());
        String name = getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        if ("v1_8_R3".equals(version)) nmsHandler = new v1_8_R3(); else if ("v1_7_R4".equals(version)) nmsHandler = new v1_7_R4(); else nmsHandler = new NMSFallback();
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
        new PublicChannel();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {//vvvvvvvvvvvvvvv
                autoUpdateManager.checkForUpdate();
            } catch (IOException e) {
                AeroClientAPI.getInstance().getLogger().info("Looks to be that github api is down, couldn't check for updates.");
            }
        }, 100);
    }
}
