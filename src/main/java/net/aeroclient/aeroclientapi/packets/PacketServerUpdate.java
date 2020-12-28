package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.io.IOException;

public class PacketServerUpdate extends ClientPacket {
  private String server;

  public void write(ByteBufWrapper b) throws IOException {
    b.writeString(this.server);
  }

  public void read(ByteBufWrapper b) throws IOException {
    this.server = b.readString();
  }

  @ConstructorProperties({"server"})
  public PacketServerUpdate(String server) {
    this.server = server;
  }

  public PacketServerUpdate() {}
  
  public String getServer() {
    return this.server;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 24 : 11;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
