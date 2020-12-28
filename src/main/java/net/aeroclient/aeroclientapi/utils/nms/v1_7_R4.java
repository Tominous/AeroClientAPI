package net.aeroclient.aeroclientapi.utils.nms;


import net.aeroclient.aeroclientapi.AeroClientAPI;
import net.aeroclient.aeroclientapi.utils.ReflectionUtil;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;

public class v1_7_R4 implements NMSHandler {
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

    @Override
    public void downloadFile(String url, String name) {
        try {
            FileUtils.copyURLToFile(
                    new URL(url),
                    new File(AeroClientAPI.getInstance().getDataFolder().getParent() + "/" + name),
                    1000,
                    1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
