package cf.cicigames.aeroclientapi.packets.voice.client;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.io.IOException;

public class PacketClientVoice extends ClientPacket {
  private byte[] data;
  

  public PacketClientVoice(byte[] data) {
    this.data = data;
  }
  
  public void write(ByteBufWrapper b) {
    int id = 16;
    b.writeVarInt(id);
    writeBlob(b, this.data);
  }


  @Override
  public void getData() {
    int id = 15;
    this.wrapper.writeVarInt(id);
    write(this.wrapper);
  }
  protected void writeBlob(ByteBufWrapper b, byte[] bytes) {
    b.buf().writeShort(bytes.length);
    b.buf().writeBytes(bytes);
  }
}
