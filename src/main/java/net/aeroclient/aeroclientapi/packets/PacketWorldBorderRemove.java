package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;

import java.beans.ConstructorProperties;
import java.io.IOException;

public class PacketWorldBorderRemove extends ClientPacket {
  private String id;
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeString(this.id);
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.id = b.readString();
  }

  
  @ConstructorProperties({"id"})
  public PacketWorldBorderRemove(String id) {
    this.id = id;
  }
  
  public PacketWorldBorderRemove() {}
  
  public String getId() {
    return this.id;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 27 : 21;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
