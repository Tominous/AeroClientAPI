package net.aeroclient.aeroclientapi.voicechat;


import net.aeroclient.aeroclientapi.packets.voice.PacketVoiceChannel;
import com.cheatbreaker.nethandler.CBPacket;
import com.cheatbreaker.nethandler.server.CBPacketVoiceChannel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.*;

public class VoiceChannelHandler {
    @Getter @Setter
    private boolean voiceEnabled;
    @Getter
    private List<VoiceChannel> voiceChannels = new ArrayList<>();
    @Getter
    private final Map<UUID, VoiceChannel> playerActiveChannels = new HashMap<>();
    @Getter
    private final Map<UUID, List<UUID>> muteMap = new HashMap<>();
    public void createVoiceChannels(VoiceChannel... voiceChannels) {
        this.voiceChannels.addAll(Arrays.asList(voiceChannels));
        for (VoiceChannel channel : voiceChannels) {
            for (Player player : channel.getPlayersInChannel())
                sendVoiceChannel(player, channel);
        }
    }

    public void deleteVoiceChannel(VoiceChannel channel) {
        this.voiceChannels.removeIf(c -> {
            boolean remove = (c == channel);
            if (remove) {
                channel.validatePlayers();
                for (Player player : channel.getPlayersInChannel()) {
                    if (getPlayerActiveChannels().get(player.getUniqueId()) == channel)
                        getPlayerActiveChannels().remove(player.getUniqueId());
                }
            }
            return remove;
        });
    }

    public void deleteVoiceChannel(UUID channelUUID) {
        getChannel(channelUUID).ifPresent(this::deleteVoiceChannel);
    }
    public void sendVoiceChannel(Player player,VoiceChannel channel) {
        channel.validatePlayers();
        new PacketVoiceChannel(channel.getUuid(), channel.getName(), channel.toPlayersMap(), channel.toListeningMap()).setTo(player).sendPacket();
    }

    public void setActiveChannel(Player player, UUID uuid) {
        getChannel(uuid).ifPresent(channel -> setActiveChannel(player, channel.getUuid()));
    }

    public Optional<VoiceChannel> getChannel(UUID uuid) {
        return this.voiceChannels.stream().filter(channel -> channel.getUuid().equals(uuid)).findFirst();
    }
    public void toggleVoiceMute(Player player, UUID other) {
        if (!this.muteMap.get(player.getUniqueId()).removeIf(uuid -> uuid.equals(other)))
            ((List<UUID>)this.muteMap.get(player.getUniqueId())).add(other);
    }

    public boolean playerHasPlayerMuted(Player player, Player other) {
        return this.muteMap.get(other.getUniqueId()).contains(player.getUniqueId());
    }
}
