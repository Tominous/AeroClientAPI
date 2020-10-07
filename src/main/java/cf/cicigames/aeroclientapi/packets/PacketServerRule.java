package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.enums.ServerRule;
import cf.cicigames.aeroclientapi.utils.ClientPacket;

public class PacketServerRule extends ClientPacket {
  ServerRule rule;
  
  boolean state;
  
  public PacketServerRule(ServerRule rule, boolean state) {
    this.rule = rule;
    this.state = state;
  }
  
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 14 : 10;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeString(this.rule.getRule());
    this.wrapper.writeBoolean(this.state);
    this.wrapper.writeInt(0);
    this.wrapper.buf().writeFloat(0.0F);
    this.wrapper.writeString("");
  }
}
