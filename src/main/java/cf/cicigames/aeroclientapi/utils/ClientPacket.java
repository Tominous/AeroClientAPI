package cf.cicigames.aeroclientapi.utils;


import java.io.ByteArrayOutputStream;

import io.netty.buffer.Unpooled;
import cf.cicigames.aeroclientapi.AeroClientAPI;
import org.bukkit.entity.Player;

public abstract class ClientPacket {
  public ByteBufWrapper wrapper = new ByteBufWrapper(Unpooled.buffer());
  private Client client;
  
  private Player to;
  public ByteArrayOutputStream os = new ByteArrayOutputStream();
  public ClientPacket setTo(Player p) {
    this.to = p;
    this.client = AeroClientAPI.getPlayerManager().getClient(p.getUniqueId());
    if (this.client == null)
      this.client = Client.AEROCLIENT; 
    getData();
    return this;
  }
  
  public abstract void getData();
  
  public void sendPacket() {
    if (AeroClientAPI.getPlayerManager().getClient(this.to.getUniqueId()) != null) {
      byte[] bytes = this.wrapper.buf().array();
      String channel = AeroClientAPI.getPlayerManager().getClient(this.to.getUniqueId()).getChannel();
      AeroClientAPI.getNmsHandler().sendPacket(channel, bytes, this.to);
    } 
  }
  
  public Client getClient() {
    return this.client;
  }
  
  public Player getTo() {
    return this.to;
  }
  
  public enum Client {
    AEROCLIENT("AC-Client"),
    LUNARCLIENT("Lunar-Client");
    
    String channel;
    
    Client(String channel) {
      this.channel = channel;
    }
    
    public String getChannel() {
      return this.channel;
    }
  }
}
