package net.aeroclient.aeroclientapi.packets.voice;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;

import java.beans.ConstructorProperties;
import java.util.UUID;

public class PacketVoiceChannelUpdate extends ClientPacket {
  public int status;
  
  private UUID channelUuid;
  
  private UUID uuid;
  
  private String name;
  
  public void write(ByteBufWrapper b) {
    b.writeVarInt(this.status);
    b.writeUUID(this.channelUuid);
    b.writeUUID(this.uuid);
    b.writeString(this.name);
  }

  
  @ConstructorProperties({"status", "channelUuid", "uuid", "name"})
  public PacketVoiceChannelUpdate(int status, UUID channelUuid, UUID uuid, String name) {
    this.status = status;
    this.channelUuid = channelUuid;
    this.uuid = uuid;
    this.name = name;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 18 : 19;
    this.wrapper.writeVarInt(id);
    write(this.wrapper);
  }
}
