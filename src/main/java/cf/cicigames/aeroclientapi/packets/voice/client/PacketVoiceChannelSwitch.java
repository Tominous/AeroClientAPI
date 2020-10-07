package cf.cicigames.aeroclientapi.packets.voice.client;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.io.IOException;
import java.util.UUID;

public class PacketVoiceChannelSwitch extends ClientPacket {
  private UUID switchingTo;
  

  public PacketVoiceChannelSwitch(UUID switchingTo) {
    this.switchingTo = switchingTo;
  }

  public void write(ByteBufWrapper b) {
    int id = 20;
    b.writeVarInt(id);
    b.writeUUID(this.switchingTo);
  }

  

  public UUID getSwitchingTo() {
    return this.switchingTo;
  }

  @Override
  public void getData() {
    write(this.wrapper);
  }
}
