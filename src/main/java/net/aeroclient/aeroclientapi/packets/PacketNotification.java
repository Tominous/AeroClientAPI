package net.aeroclient.aeroclientapi.packets;

import net.aeroclient.aeroclientapi.enums.Notification;
import net.aeroclient.aeroclientapi.utils.ClientPacket;
import org.bukkit.Bukkit;

public class PacketNotification extends ClientPacket {
    Notification type;
    int time;
    String message;
    public PacketNotification(Notification type, String message, int time) {
        this.type = type;
        this.time = time;
        this.message = message;
    }

    public void getData() {
        int id = getClient().getChannel().equals("AC-Client") ? 4 : 9;
        this.wrapper.writeVarInt(id);
        this.wrapper.writeString(this.message);
        this.wrapper.buf().writeLong(this.time);
        this.wrapper.writeString(this.type.getType());
    }
}
