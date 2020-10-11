package cf.cicigames.aeroclientapi.utils;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ByteBufWrapper {
  private final ByteBuf buf;
  
  public ByteBufWrapper(ByteBuf buf) {
    this.buf = buf;
  }
  
  public void writeVarInt(int b) {
    while ((b & 0xFFFFFF80) != 0) {
      this.buf.writeByte(b & 0x7F | 0x80);
      b >>>= 7;
    } 
    this.buf.writeByte(b);
  }
  
  public int readVarInt() {
    byte b;
    int i = 0;
    int chunk = 0;
    do {
      b = this.buf.readByte();
      i |= (b & Byte.MAX_VALUE) << chunk++ * 7;
      if (chunk > 5)
        throw new RuntimeException("VarInt too big"); 
    } while ((b & 0x80) == 128);
    return i;
  }
  
  public <T> void writeOptional(T obj, Consumer<T> consumer) {
    this.buf.writeBoolean((obj != null));
    if (obj != null)
      consumer.accept(obj); 
  }
  
  public <T> T readOptional(Supplier<T> supplier) {
    boolean isPresent = this.buf.readBoolean();
    return isPresent ? supplier.get() : null;
  }
  
  public void writeString(String s) {
    byte[] arr = s.getBytes(Charsets.UTF_8);
    writeVarInt(arr.length);
    this.buf.writeBytes(arr);
  }
  
  public String readString() {
    int len = readVarInt();
    byte[] buffer = new byte[len];
    this.buf.readBytes(buffer);
    return new String(buffer, Charsets.UTF_8);
  }
  
  public void writeBoolean(boolean bool) {
    this.buf.writeBoolean(bool);
  }
  
  public void writeInt(int i) {
    this.buf.writeInt(i);
  }
  
  public void writeUUID(UUID uuid) {
    this.buf.writeLong(uuid.getMostSignificantBits());
    this.buf.writeLong(uuid.getLeastSignificantBits());
  }
  
  public UUID readUUID() {
    long mostSigBits = this.buf.readLong();
    long leastSigBits = this.buf.readLong();
    return new UUID(mostSigBits, leastSigBits);
  }
  
  public ByteBuf buf() {
    return this.buf;
  }
}
