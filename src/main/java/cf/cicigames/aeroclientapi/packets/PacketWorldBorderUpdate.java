package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.io.IOException;

public class PacketWorldBorderUpdate extends ClientPacket {
  private String id;
  
  private double minX;
  
  private double minZ;
  
  private double maxX;
  
  private double maxZ;
  
  private int durationTicks;
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeString(this.id);
    b.buf().writeDouble(this.minX);
    b.buf().writeDouble(this.minZ);
    b.buf().writeDouble(this.maxX);
    b.buf().writeDouble(this.maxZ);
    b.buf().writeInt(this.durationTicks);
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.id = b.readString();
    this.minX = b.buf().readDouble();
    this.minZ = b.buf().readDouble();
    this.maxX = b.buf().readDouble();
    this.maxZ = b.buf().readDouble();
    this.durationTicks = b.buf().readInt();
  }
  

  public String getId() {
    return this.id;
  }
  
  public double getMinX() {
    return this.minX;
  }
  
  public double getMinZ() {
    return this.minZ;
  }
  
  public double getMaxX() {
    return this.maxX;
  }
  
  public double getMaxZ() {
    return this.maxZ;
  }
  
  public int getDurationTicks() {
    return this.durationTicks;
  }
  
  public PacketWorldBorderUpdate() {}
  
  @ConstructorProperties({"id", "minX", "minZ", "maxX", "maxZ", "durationTicks"})
  public PacketWorldBorderUpdate(String id, double minX, double minZ, double maxX, double maxZ, int durationTicks) {
    this.id = id;
    this.minX = minX;
    this.minZ = minZ;
    this.maxX = maxX;
    this.maxZ = maxZ;
    this.durationTicks = durationTicks;
  }
  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 26 : 22;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
