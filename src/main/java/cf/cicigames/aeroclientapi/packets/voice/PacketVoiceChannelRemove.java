package cf.cicigames.aeroclientapi.packets.voice;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.UUID;

public class PacketVoiceChannelRemove extends ClientPacket {
  private UUID uuid;
  


  @ConstructorProperties({"uuid"})
  public PacketVoiceChannelRemove(UUID uuid) {
    this.uuid = uuid;
  }
  

  public UUID getUuid() {
    return this.uuid;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 21 : 18;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeUUID(this.uuid);
  }
}
