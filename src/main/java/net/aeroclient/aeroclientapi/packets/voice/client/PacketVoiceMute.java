package net.aeroclient.aeroclientapi.packets.voice.client;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;

import java.util.UUID;

public class PacketVoiceMute extends ClientPacket {
  private UUID muting;
  
  public PacketVoiceMute() {}
  
  public PacketVoiceMute(UUID muting) {
    this.muting = muting;
  }
  
  public void write(ByteBufWrapper b) {
    int id = getClient().getChannel().equals("AC-Client") ? 19 : 2;
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
