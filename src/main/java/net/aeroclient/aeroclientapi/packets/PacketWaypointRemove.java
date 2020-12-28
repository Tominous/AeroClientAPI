package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ClientPacket;
import com.cheatbreaker.nethandler.ByteBufWrapper;
import com.cheatbreaker.nethandler.CBPacket;
import com.cheatbreaker.nethandler.ICBNetHandler;
import com.google.common.base.Preconditions;

public class PacketWaypointRemove extends ClientPacket {
  private String name;
  
  private String world;
  
  public PacketWaypointRemove(String name, String world) {
    this.name = (String)Preconditions.checkNotNull(name, "name");
    this.world = (String)Preconditions.checkNotNull(world, "world");
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 2 : 24;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeString(this.name);
    this.wrapper.writeString(this.world);
  }
}
