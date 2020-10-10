package cf.cicigames.aeroclientapi.utils.nms;

import org.bukkit.entity.Player;

public interface NMSHandler {
    void sendPacket(String channel, byte[] bytes, Player player);
    void registerChannel(Player player);
    void downloadFile(String url, String name);
}
