package net.aeroclient.aeroclientapi.voicechat;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import com.cheatbreaker.api.CheatBreakerAPI;
import com.cheatbreaker.api.voice.VoiceChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PublicChannel {
    public void enableVoiceChat() {
        AeroClientAPI.getVoiceChannelHandler().setVoiceEnabled(true);
        final VoiceChannel publicVoiceChannel = new VoiceChannel("Public Voice 1");
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
