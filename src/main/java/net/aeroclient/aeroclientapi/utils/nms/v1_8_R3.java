package net.aeroclient.aeroclientapi.utils.nms;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import net.aeroclient.aeroclientapi.utils.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
