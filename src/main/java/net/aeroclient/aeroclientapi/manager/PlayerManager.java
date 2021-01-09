package net.aeroclient.aeroclientapi.manager;

import net.aeroclient.aeroclientapi.utils.ClientPacket;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerManager {
  @Getter
  private Set<UUID> onLunarClient = new HashSet<>();
  @Getter
  private Set<UUID> onAeroClient = new HashSet<>();
  @Getter
  private Set<UUID> onCheatBreaker = new HashSet<>();
  @Getter
  private Set<UUID> onForge = new HashSet<>();
  public void addToLC(Player p) {
    this.onLunarClient.add(p.getUniqueId());
  }
  
  public void addToAC(Player p) {
    this.onAeroClient.add(p.getUniqueId());
  }

  public void removeFromLC(Player p) {
    if (this.onLunarClient.contains(p.getUniqueId()))
      this.onLunarClient.remove(p.getUniqueId()); 
  }
  
  public void removeFromAC(Player p) {
    if (this.onAeroClient.contains(p.getUniqueId()))
      this.onAeroClient.remove(p.getUniqueId()); 
  }
  public ClientPacket.Client getClient(UUID uuid) {
    if(onAeroClient.contains(uuid)) {
      return ClientPacket.Client.AEROCLIENT;
    } else if(onLunarClient.contains(uuid)) {
     return ClientPacket.Client.LUNARCLIENT;
    } else if(onCheatBreaker.contains(uuid)) {
      return ClientPacket.Client.CHEATBREAKER;
    } else if (onForge.contains(uuid)) {
      return ClientPacket.Client.FORGE;
    } else {
      return ClientPacket.Client.VANILLA;
    }
  }
  
  public boolean vanilla(UUID uuid) {
    return (!this.onAeroClient.contains(uuid) && !this.onLunarClient.contains(uuid));
  }
  
  public Set<UUID> getOnLC() {
    return this.onLunarClient;
  }
  
  public Set<UUID> getOnAeroClient() {
    return this.onAeroClient;
  }
}