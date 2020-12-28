package net.aeroclient.aeroclientapi.packets.voice.client;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;


import java.util.UUID;

public class PacketVoiceChannelSwitch extends ClientPacket {
  private UUID switchingTo;
  

  public PacketVoiceChannelSwitch(UUID switchingTo) {
    this.switchingTo = switchingTo;
  }

  public void write(ByteBufWrapper b) {
    int id = getClient().getChannel().equals("AC-Client") ? 20 : 1;
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
