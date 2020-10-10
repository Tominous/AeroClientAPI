package cf.cicigames.aeroclientapi.packets;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;


import java.io.IOException;
import java.util.*;

public class PacketNametagsUpdate extends ClientPacket {
  private Map<UUID, List<String>> playersMap;

  public PacketNametagsUpdate(Map<UUID, List<String>> playersMap) {
    this.playersMap = playersMap;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeVarInt((this.playersMap == null) ? -1 : this.playersMap.size());
    if (this.playersMap != null)
      for (Map.Entry<UUID, List<String>> uuidListEntry : this.playersMap.entrySet()) {
        UUID uuid = uuidListEntry.getKey();
        List<String> tags = uuidListEntry.getValue();
        b.writeUUID(uuid);
        b.writeVarInt(tags.size());
        for (String s : tags)
          b.writeString(s); 
      }  
  }

  public Map<UUID, List<String>> getPlayersMap() {
    return this.playersMap;
  }

  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 6 : 8;
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
