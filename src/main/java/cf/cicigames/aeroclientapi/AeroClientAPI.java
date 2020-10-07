package cf.cicigames.aeroclientapi;

import cf.cicigames.aeroclientapi.command.*;
import cf.cicigames.aeroclientapi.manager.BanWaveManager;
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

public final class AeroClientAPI extends JavaPlugin {
    @Getter
    private static AeroClientAPI instance;
    @Getter
    private static PlayerManager playerManager;
    @Getter
    public static NMSHandler nmsHandler;
    @Getter
    public static VoiceChannelHandler voiceChannelHandler;
    @Getter
    public static BanWaveManager banWaveManager;
    @Override
    public void onEnable() {
        instance = this;
        playerManager = new PlayerManager();
        voiceChannelHandler = new VoiceChannelHandler();
        banWaveManager = new BanWaveManager();
        Bukkit.getPluginManager().registerEvents((Listener)new ClientLoginListener(), (Plugin)this);

        // Plugin startup logic
        getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "AC-Client");
        getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "Lunar-Client");

        getCommand("clients").setExecutor(new ClientUsersCommand());
        getCommand("aacinfo").setExecutor(new APIInfoCommand());
        getCommand("aachelp").setExecutor(new APIHelpCommand());
        getCommand("client").setExecutor(new AeroClientCommand());
        getCommand("clientban").setExecutor(new ClientBanCommand());
        getCommand("clientunban").setExecutor(new ClientUnbanCommand());
        getCommand("staffmods").setExecutor(new StaffModsCommand());
        getCommand("banwave").setExecutor(new BanwaveCommand());
        String name = getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1);
        switch(version) {
            case "v1_8_R3":
                nmsHandler = new v1_8_R3();
                break;
            default:
                nmsHandler = new Fallback();
                break;
        }
        new PublicChannel();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
