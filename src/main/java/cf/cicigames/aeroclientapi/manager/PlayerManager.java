package cf.cicigames.aeroclientapi.manager;

import cf.cicigames.aeroclientapi.utils.ClientPacket;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerManager {
  private Set<UUID> onLunarClient = new HashSet<>();
  
  private Set<UUID> onAeroClient = new HashSet<>();
  
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
    if (this.onAeroClient.contains(uuid))
      return ClientPacket.Client.AEROCLIENT; 
    return this.onLunarClient.contains(uuid) ? ClientPacket.Client.LUNARCLIENT : null;
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