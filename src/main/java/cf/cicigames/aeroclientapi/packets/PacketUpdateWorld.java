package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ClientPacket;

public class PacketUpdateWorld extends ClientPacket {
  String world;
  
  public PacketUpdateWorld(String world) {
    this.world = world;
  }
  
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 23 : 15;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeString(this.world);
  }
}
