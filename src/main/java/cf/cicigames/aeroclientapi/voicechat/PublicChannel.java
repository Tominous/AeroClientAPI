package cf.cicigames.aeroclientapi.voicechat;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.voice.VoiceChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PublicChannel {
    public void onEnable() {
        AeroClientAPI.getVoiceChannelHandler().setVoiceEnabled(true);
        final VoiceChannel publicVoiceChannel = new VoiceChannel("Voice Channel");
        CheatBreakerAPI.getInstance().createVoiceChannels(new VoiceChannel[] { publicVoiceChannel });
        AeroClientAPI.getInstance().getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event) {
                publicVoiceChannel.addPlayer(event.getPlayer());
                CheatBreakerAPI.getInstance().setActiveChannel(event.getPlayer(), publicVoiceChannel);
            }
        }, AeroClientAPI.getInstance());
    }
}
