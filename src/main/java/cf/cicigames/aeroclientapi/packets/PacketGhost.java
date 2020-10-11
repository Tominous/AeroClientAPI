package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketGhost extends ClientPacket {
  private List<UUID> uuidList;
  
  public PacketGhost() {}
  
  @ConstructorProperties({"uuidList"})
  public PacketGhost(List<UUID> uuidList) {
    this.uuidList = uuidList;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeVarInt(this.uuidList.size());
    for (UUID uuid : this.uuidList)
      b.writeUUID(uuid); 
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    int uuidListSize = b.readVarInt();
    this.uuidList = new ArrayList<>();
    for (int i = 0; i < uuidListSize; i++)
      this.uuidList.add(b.readUUID()); 
  }

  public List<UUID> getUuidList() {
    return this.uuidList;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 28 : 25;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
