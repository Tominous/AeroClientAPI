package net.aeroclient.aeroclientapi.packets.voice;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.util.UUID;

public class PacketVoice extends ClientPacket {
  private UUID uuid;
  
  private byte[] data;

  


  @ConstructorProperties({"uuid", "data"})
  public PacketVoice(UUID uuid, byte[] data) {
    this.uuid = uuid;
    this.data = data;
  }
  
  protected void writeBlob(ByteBufWrapper b, byte[] bytes) {
    b.buf().writeShort(bytes.length);
    b.buf().writeBytes(bytes);
  }

  @Override
  public void getData() {
    int id = 16;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeUUID(this.uuid);
    writeBlob(this.wrapper, this.data);
  }
}
