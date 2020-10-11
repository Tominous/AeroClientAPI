package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketHologramUpdate extends ClientPacket {
  private UUID uuid;
  
  private List<String> lines;
  

  public PacketHologramUpdate(UUID uuid, List<String> lines) {
    this.uuid = uuid;
    this.lines = lines;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeUUID(this.uuid);
    b.writeVarInt(this.lines.size());
    for (String s : this.lines)
      b.writeString(s); 
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.uuid = b.readUUID();
    int linesSize = b.readVarInt();
    this.lines = new ArrayList<>();
    for (int i = 0; i < linesSize; i++)
      this.lines.add(b.readString()); 
  }
  public UUID getUuid() {
    return this.uuid;
  }
  
  public List<String> getLines() {
    return this.lines;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 10 : 5;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
