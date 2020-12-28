package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PacketNametagsOverride extends ClientPacket {
  private UUID player;
  
  private List<String> tags;
  

  public PacketNametagsOverride(UUID player, List<String> tags) {
    this.player = player;
    this.tags = tags;
  }
  
  public void write(ByteBufWrapper b) throws IOException {
    b.writeUUID(this.player);
    Iterator<String> iterator = null;
    AtomicReference<String> s = null;
    b.writeOptional(this.tags, t -> {
          b.writeVarInt(t.size());
          t.iterator();
          while (iterator.hasNext()) {
            s.set(iterator.next());
            b.writeString(s.get());
          } 
        });
  }

  

  public UUID getPlayer() {
    return this.player;
  }
  
  public List<String> getTags() {
    return this.tags;
  }

    @Override
    public void getData() {
        int id = getClient().getChannel().equals("AC-Client") ? 8 : 7;
      this.wrapper.writeVarInt(id);
        try {
            write(this.wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
