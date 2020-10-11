package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ClientPacket;

public class PacketCooldown extends ClientPacket {
  private String message;
  
  private long duration;
  
  private int itemid;
  
  public PacketCooldown(String message, long duration, int item) {
    this.message = message;
    this.duration = duration;
    this.itemid = item;
  }
  
  public void getData() {
    int id = 3;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeString(this.message);
    this.wrapper.buf().writeLong(this.duration);
    this.wrapper.writeInt(this.itemid);
  }
}
