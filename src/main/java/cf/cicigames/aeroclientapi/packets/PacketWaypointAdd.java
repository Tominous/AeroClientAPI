package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;

import java.io.IOException;

public class PacketWaypointAdd extends ClientPacket {
  private String name;
  
  private String world;
  
  private int color;
  
  private int x;
  
  private int y;
  
  private int z;
  
  private boolean forced;
  
  private boolean visible;
  
  public PacketWaypointAdd(String name, String world, int color, int x, int y, int z, boolean forced, boolean visible) {
    this.name = name;
    this.world = world;
    this.color = color;
    this.x = x;
    this.y = y;
    this.z = z;
    this.forced = forced;
    this.visible = visible;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 0 : 23;
    this.wrapper.writeVarInt(id);

    this.wrapper.writeString(this.name);
    this.wrapper.writeString(this.world);
    this.wrapper.buf().writeInt(this.color);
    this.wrapper.buf().writeInt(this.x);
    this.wrapper.buf().writeInt(this.y);
    this.wrapper.buf().writeInt(this.z);
    this.wrapper.buf().writeBoolean(this.forced);
    this.wrapper.buf().writeBoolean(this.visible);
  }
}
