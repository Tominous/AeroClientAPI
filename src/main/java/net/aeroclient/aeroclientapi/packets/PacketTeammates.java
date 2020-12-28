package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.utils.ByteBufWrapper;
import net.aeroclient.aeroclientapi.utils.ClientPacket;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketTeammates extends ClientPacket {
  private UUID leader;

  private long lastMs;

  private Map<UUID, Map<String, Double>> players;

  public void write(ByteBufWrapper b) throws IOException {
    b.buf().writeBoolean((this.leader != null));
    if (this.leader != null)
      b.writeUUID(this.leader);
    b.buf().writeLong(this.lastMs);
    b.writeVarInt(this.players.values().size());
    for (Map.Entry<UUID, Map<String, Double>> entry : this.players.entrySet()) {
      b.writeUUID(entry.getKey());
      b.writeVarInt(((Map)entry.getValue()).values().size());
      for (Map.Entry<String, Double> stringDoubleEntry : (Iterable<Map.Entry<String, Double>>)((Map)entry.getValue()).entrySet()) {
        b.writeString(stringDoubleEntry.getKey());
        b.buf().writeDouble(((Double)stringDoubleEntry.getValue()).doubleValue());
      }
    }
  }

  public void read(ByteBufWrapper b) throws IOException {
    boolean hasLeader = b.buf().readBoolean();
    if (hasLeader)
      this.leader = b.readUUID();
    this.lastMs = b.buf().readLong();
    int playersSize = b.readVarInt();
    this.players = new HashMap<>();
    for (int i = 0; i < playersSize; i++) {
      UUID uuid = b.readUUID();
      int posMapSize = b.readVarInt();
      Map<String, Double> posMap = new HashMap<>();
      for (int j = 0; j < posMapSize; j++) {
        String key = b.readString();
        double val = b.buf().readDouble();
        posMap.put(key, Double.valueOf(val));
      }
      this.players.put(uuid, posMap);
    }
  }

  public UUID getLeader() {
    return this.leader;
  }

  public long getLastMs() {
    return this.lastMs;
  }

  public Map<UUID, Map<String, Double>> getPlayers() {
    return this.players;
  }

  @ConstructorProperties({"leader", "lastMs", "players"})
  public PacketTeammates(UUID leader, long lastMs, Map<UUID, Map<String, Double>> players) {
    this.leader = leader;
    this.lastMs = lastMs;
    this.players = players;
  }

  public PacketTeammates() {}
  @Override
  public void getData() {
    int id = getClient().getChannel().equals("AC-Client") ? 7 : 13;
    this.wrapper.writeVarInt(id);
    try {
      write(this.wrapper);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
