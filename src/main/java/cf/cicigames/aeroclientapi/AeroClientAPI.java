package cf.cicigames.aeroclientapi;

import cf.cicigames.aeroclientapi.command.*;
import cf.cicigames.aeroclientapi.manager.AutoUpdateManager;
import cf.cicigames.aeroclientapi.manager.BanWaveManager;
import cf.cicigames.aeroclientapi.manager.ConfigManager;
import cf.cicigames.aeroclientapi.utils.nms.Fallback;
import cf.cicigames.aeroclientapi.voicechat.PublicChannel;
import cf.cicigames.aeroclientapi.voicechat.VoiceChannelHandler;
import lombok.Getter;
import cf.cicigames.aeroclientapi.listeners.ClientLoginListener;
import cf.cicigames.aeroclientapi.manager.PlayerManager;
import cf.cicigames.aeroclientapi.utils.nms.NMSHandler;
import cf.cicigames.aeroclientapi.utils.nms.v1_8_R3;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
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

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        voiceChannelHandler = new VoiceChannelHandler();
        banWaveManager = new BanWaveManager();
        autoUpdateManager = new AutoUpdateManager();
        configManager = new ConfigManager();
        Bukkit.getPluginManager().registerEvents(new ClientLoginListener(), this);

        // Plugin startup logic
        getServer().getMessenger().registerOutgoingPluginChannel(this, "AC-Client");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Lunar-Client");
        configManager.load();
        getCommand("clients").setExecutor(new ClientUsersCommand());
        getCommand("aacinfo").setExecutor(new APIInfoCommand());
        getCommand("aachelp").setExecutor(new APIHelpCommand());
        getCommand("client").setExecutor(new AeroClientCommand());
        getCommand("clientban").setExecutor(new ClientBanCommand());
        getCommand("clientunban").setExecutor(new ClientUnbanCommand());
        getCommand("staffmods").setExecutor(new StaffModsCommand());
        getCommand("banwave").setExecutor(new BanwaveCommand());
        getCommand("aacreload").setExecutor(new APIReload());
        String name = getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        if ("v1_8_R3".equals(version)) nmsHandler = new v1_8_R3(); else nmsHandler = new Fallback();
        new PublicChannel();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                autoUpdateManager.checkForUpdate();
            } catch (IOException ex) {
                AeroClientAPI.getInstance().getLogger().info("Looks to be that github api is down, couldn't check for updates.");
            }
        }, 100);
    }
}
