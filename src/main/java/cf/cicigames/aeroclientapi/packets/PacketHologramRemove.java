package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;

import java.io.IOException;
import java.util.UUID;

public class PacketHologramRemove extends ClientPacket {
  private UUID uuid;
  
  public PacketHologramRemove() {}
  
  public PacketHologramRemove(UUID uuid) {
    this.uuid = uuid;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeUUID(this.uuid);
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.uuid = b.readUUID();
  }

  public UUID getUuid() {
    return this.uuid;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 11 : 6;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
