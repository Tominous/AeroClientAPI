package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ClientPacket;
import lombok.SneakyThrows;
import net.aeroclient.aeroclientapi.enums.StaffModule;

public class PacketStaffMod extends ClientPacket {
  StaffModule mod;
  
  boolean state;
  
  public PacketStaffMod(StaffModule mod, boolean state) {
    this.mod = mod;
    this.state = state;
  }
  
  @SneakyThrows
  public void getData() {
      int id = getClient().getChannel().equals("AC-Client") ? 5 : 12;
      this.wrapper.writeVarInt(id);
      this.wrapper.writeString(this.mod.getPacketName());
      this.wrapper.writeBoolean(this.state);

  }
}
