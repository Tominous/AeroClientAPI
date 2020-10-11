package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.beans.ConstructorProperties;
import java.io.IOException;

public class PacketWorldBorder extends ClientPacket {
  private String id;
  
  private String world;
  
  private boolean cancelsExit;
  
  private boolean canShrinkExpand;
  
  private int color;
  
  private double minX;
  
  private double minZ;
  
  private double maxX;
  
  private double maxZ;
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeOptional(this.id, b::writeString);
    b.writeString(this.world);
    b.buf().writeBoolean(this.cancelsExit);
    b.buf().writeBoolean(this.canShrinkExpand);
    b.buf().writeInt(this.color);
    b.buf().writeDouble(this.minX);
    b.buf().writeDouble(this.minZ);
    b.buf().writeDouble(this.maxX);
    b.buf().writeDouble(this.maxZ);
  }
  
  public void read(ByteBufWrapper b) throws IOException {
    this.id = (String)b.readOptional(b::readString);
    this.world = b.readString();
    this.cancelsExit = b.buf().readBoolean();
    this.canShrinkExpand = b.buf().readBoolean();
    this.color = b.buf().readInt();
    this.minX = b.buf().readDouble();
    this.minZ = b.buf().readDouble();
    this.maxX = b.buf().readDouble();
    this.maxZ = b.buf().readDouble();
  }

  public String getId() {
    return this.id;
  }
  
  public String getWorld() {
    return this.world;
  }
  
  public boolean isCancelsExit() {
    return this.cancelsExit;
  }
  
  public boolean isCanShrinkExpand() {
    return this.canShrinkExpand;
  }
  
  public int getColor() {
    return this.color;
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
  
  public PacketWorldBorder() {
    this.color = -13421569;
  }
  
  @ConstructorProperties({"id", "world", "cancelsExit", "canShrinkExpand", "color", "minX", "minZ", "maxX", "maxZ"})
  public PacketWorldBorder(String id, String world, boolean cancelsExit, boolean canShrinkExpand, int color, double minX, double minZ, double maxX, double maxZ) {
    this.color = -13421569;
    this.id = id;
    this.world = world;
    this.cancelsExit = cancelsExit;
    this.canShrinkExpand = canShrinkExpand;
    this.color = color;
    this.minX = minX;
    this.minZ = minZ;
    this.maxX = maxX;
    this.maxZ = maxZ;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 25 : 20;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
