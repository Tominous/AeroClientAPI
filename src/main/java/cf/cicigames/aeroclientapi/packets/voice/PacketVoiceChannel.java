package cf.cicigames.aeroclientapi.packets.voice;

import cf.cicigames.aeroclientapi.utils.ByteBufWrapper;
import cf.cicigames.aeroclientapi.utils.ClientPacket;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketVoiceChannel extends ClientPacket {
  private UUID uuid;
  
  private String name;
  
  private Map<UUID, String> players;
  
  private Map<UUID, String> listening;

  
  private void writeMap(ByteBufWrapper b, Map<UUID, String> players) {
    b.writeVarInt(players.size());
    for (Map.Entry<UUID, String> uuidStringEntry : players.entrySet()) {
      b.writeUUID(uuidStringEntry.getKey());
      b.writeString(uuidStringEntry.getValue());
    } 
  }
  

  

  @ConstructorProperties({"uuid", "name", "players", "listening"})
  public PacketVoiceChannel(UUID uuid, String name, Map<UUID, String> players, Map<UUID, String> listening) {
    this.uuid = uuid;
    this.name = name;
    this.players = players;
    this.listening = listening;
  }


  @Override
  public void getData() {
    int id = 17;
    this.wrapper.writeVarInt(id);
    this.wrapper.writeUUID(this.uuid);
    this.wrapper.writeString(this.name);
    writeMap(this.wrapper, this.players);
    writeMap(this.wrapper, this.listening);
  }
}
