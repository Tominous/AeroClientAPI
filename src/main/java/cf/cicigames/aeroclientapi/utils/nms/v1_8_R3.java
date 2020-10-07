package cf.cicigames.aeroclientapi.utils.nms;

import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import cf.cicigames.aeroclientapi.utils.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.entity.Player;

public class v1_8_R3 implements NMSHandler {

    @SneakyThrows
    public void sendPacket(String channel, byte[] bytes, Player player) {
        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, new PacketDataSerializer(Unpooled.wrappedBuffer(bytes)));
        ReflectionUtil.sendPacket(player, packet);
    }

    @SneakyThrows
    @Override
    public void registerChannel(Player player) {
        PacketPlayOutCustomPayload constructor = new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("Lunar-Client".getBytes())));
        ReflectionUtil.sendPacket(player, constructor);
        PacketPlayOutCustomPayload constructor1 = new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("AC-Client".getBytes())));
        ReflectionUtil.sendPacket(player, constructor1);
    }
}
