package cf.cicigames.aeroclientapi.voicechat;


import cf.cicigames.aeroclientapi.AeroClientAPI;
import cf.cicigames.aeroclientapi.packets.voice.PacketVoiceChannelUpdate;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class VoiceChannel {
  private final String name;
  
  private final UUID uuid;
  
  public String getName() {
    return this.name;
  }
  
  public UUID getUuid() {
    return this.uuid;
  }
  
  private final List<Player> playersInChannel = new ArrayList<>();
  
  public List<Player> getPlayersInChannel() {
    return this.playersInChannel;
  }
  
  private final List<Player> playersListening = new ArrayList<>();
  
  public List<Player> getPlayersListening() {
    return this.playersListening;
  }
  
  public VoiceChannel(String name) {
    this.name = name;
    this.uuid = UUID.randomUUID();
  }
  
  public void addPlayer(Player player) {
    if (hasPlayer(player))
      return; 
    for (Player player1 : this.playersInChannel)
      new PacketVoiceChannelUpdate(0, this.uuid, player.getUniqueId(), player.getDisplayName()).setTo(player1).sendPacket();
    this.playersInChannel.add(player);
    //CheatBreakerAPI.getInstance().sendVoiceChannel(player, this);
  }
  
  public boolean removePlayer(Player player) {
    if (!hasPlayer(player))
      return false; 
    for (Player player1 : this.playersInChannel) {
      if (player1 == player)
        continue; 
        new PacketVoiceChannelUpdate(1, this.uuid, player.getUniqueId(), player.getDisplayName()).setTo(player1).sendPacket();
    } 
//    CheatBreakerAPI.getInstance().getPlayerActiveChannels().remove(player.getUniqueId());
    this.playersListening.removeIf(player1 -> (player1 == player));
    return this.playersInChannel.removeIf(player1 -> (player1 == player));
  }
  
  private boolean addListening(Player player) {
    if (!hasPlayer(player) || isListening(player))
      return false; 
    this.playersListening.add(player);
    for (Player player1 : this.playersInChannel)
      new PacketVoiceChannelUpdate(2, this.uuid, player.getUniqueId(), player.getDisplayName()).setTo(player1).sendPacket();
    return true;
  }
  
  public boolean removeListening(Player player) {
    if (!isListening(player))
      return false; 
    for (Player player1 : this.playersInChannel) {
      if (player1 == player)
        continue;
      new PacketVoiceChannelUpdate(3, this.uuid, player.getUniqueId(), player.getDisplayName()).setTo(player1).sendPacket();
    } 
    return this.playersListening.removeIf(player1 -> (player1 == player));
  }
  
  public void setActive(Player player) {
    VoiceChannelHandler api = AeroClientAPI.getVoiceChannelHandler();
    Optional.ofNullable(api.getPlayerActiveChannels().get(player.getUniqueId())).ifPresent(c -> {
          if (c != this)
            c.removeListening(player); 
        });
    if (addListening(player))
      api.getPlayerActiveChannels().put(player.getUniqueId(), this); 
  }
  
  public boolean validatePlayers() {
    return (this.playersInChannel.removeIf(Objects::isNull) || this.playersListening.removeIf(player -> !this.playersInChannel.contains(player)));
  }
  
  public boolean hasPlayer(Player player) {
    return this.playersInChannel.contains(player);
  }
  
  public boolean isListening(Player player) {
    return this.playersListening.contains(player);
  }
  
  public Map<UUID, String> toPlayersMap() {
    return (Map<UUID, String>)this.playersInChannel.stream()
      .collect(Collectors.toMap(OfflinePlayer::getUniqueId, Player::getDisplayName));
  }
  
  public Map<UUID, String> toListeningMap() {
    return (Map<UUID, String>)this.playersListening.stream()
      .collect(Collectors.toMap(OfflinePlayer::getUniqueId, Player::getDisplayName));
  }
}
