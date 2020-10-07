package cf.cicigames.aeroclientapi.packets.voice.client;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;

import java.io.IOException;
import java.util.UUID;

public class PacketVoiceMute extends ClientPacket {
  private UUID muting;
  
  public PacketVoiceMute() {}
  
  public PacketVoiceMute(UUID muting) {
    this.muting = muting;
  }
  
  public void write(ByteBufWrapper b) {
    int id = 19;
    b.writeVarInt(id);
    b.writeUUID(this.muting);
  }
  

  

  public UUID getMuting() {
    return this.muting;
  }

  @Override
  public void getData() {
    write(this.wrapper);
  }
}
