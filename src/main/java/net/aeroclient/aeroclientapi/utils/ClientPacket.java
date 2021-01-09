package net.aeroclient.aeroclientapi.utils;


import java.io.ByteArrayOutputStream;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import net.minecraft.util.io.netty.buffer.Unpooled;
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
    if (AeroClientAPI.getPlayerManager().getClient(this.to.getUniqueId()) != Client.VANILLA && AeroClientAPI.getPlayerManager().getClient(this.to.getUniqueId()) != Client.FORGE) {
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
    AEROCLIENT("AC-Client", "Aero Client"),
    LUNARCLIENT("Lunar-Client", "Lunar Client"),
    CHEATBREAKER("CB-Client", "CheatBreaker"),
    FORGE("FML", "Forge"),
    VANILLA("Vanilla", "Vanilla");

    String channel;
    String name;

    Client(String channel, String name) {
      this.channel = channel;
      this.name = name;
    }
    
    public String getChannel() {
      return this.channel;
    }
    public String getName() {
      return this.name;
    }
  }
}
