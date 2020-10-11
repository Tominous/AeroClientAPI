package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketHologram extends ClientPacket {
  private UUID uuid;
  
  private double x;
  
  private double y;
  
  private double z;
  
  private List<String> lines;
  
  public PacketHologram() {}
  
  public PacketHologram(UUID uuid, double x, double y, double z, List<String> lines) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.z = z;
    this.lines = lines;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeUUID(this.uuid);
    b.buf().writeDouble(this.x);
    b.buf().writeDouble(this.y);
    b.buf().writeDouble(this.z);
    b.writeVarInt(this.lines.size());
    for (String s : this.lines)
      b.writeString(s); 
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.uuid = b.readUUID();
    this.x = b.buf().readDouble();
    this.y = b.buf().readDouble();
    this.z = b.buf().readDouble();
    int linesSize = b.readVarInt();
    this.lines = new ArrayList<>();
    for (int i = 0; i < linesSize; i++)
      this.lines.add(b.readString()); 
  }
  

  public UUID getUuid() {
    return this.uuid;
  }
  
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public double getZ() {
    return this.z;
  }
  
  public List<String> getLines() {
    return this.lines;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 9 : 4;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
