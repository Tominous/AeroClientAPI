package cf.cicigames.aeroclientapi.utils.nms;


import cf.cicigames.aeroclientapi.utils.ReflectionUtil;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Fallback implements NMSHandler {
    @Override
    public void sendPacket(String channel, byte[] bytes, Player player) {
        try {

            Constructor<?> constructor = ReflectionUtil.getClass("PacketPlayOutCustomPayload").getConstructor(String.class, ByteBuf.class);
            Constructor<?> serializerConstructor = ReflectionUtil.getClass("PacketDataSerializer").getConstructor(ByteBuf.class);
            Object packet = constructor.newInstance(channel, serializerConstructor.newInstance(Unpooled.wrappedBuffer(bytes)));
            ReflectionUtil.sendPacket(player, packet);
        } catch (Exception var6) {
            var6.printStackTrace();
            player.sendMessage("§cFailed to server name. Please contact a developer.");
        }
    }

    @Override
    public void registerChannel(Player p) {
        try {
            Constructor constructor = ReflectionUtil.getClass("PacketPlayOutCustomPayload").getConstructor(new Class[]{String.class, ByteBuf.class});
            Constructor serializerConstructor = ReflectionUtil.getClass("PacketDataSerializer").getConstructor(new Class[]{ByteBuf.class});
            Object packet = constructor.newInstance(new Object[]{"REGISTER", serializerConstructor.newInstance(new Object[]{Unpooled.wrappedBuffer("Lunar-Client".getBytes())})});
            ReflectionUtil.sendPacket(p, packet);
            packet = constructor.newInstance(new Object[]{"REGISTER", serializerConstructor.newInstance(new Object[]{Unpooled.wrappedBuffer("AC-Client".getBytes())})});
            ReflectionUtil.sendPacket(p, packet);
        } catch (Exception var4) {
            var4.printStackTrace();

        }
    }
}
