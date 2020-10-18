package cf.cicigames.aeroclientapi.manager;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import cf.cicigames.aeroclientapi.packets.PacketNametagsOverride;
import cf.cicigames.aeroclientapi.utils.hcfCores.HCFCoreHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomNameTagsManager {

    private final ArrayList<String> namesTags = AeroClientAPI.getConfigManager().nameTag;
    public boolean enabled = AeroClientAPI.getConfigManager().customNametag;
    public HCFCoreHandler hcfCore;
    public void update(Player player) {//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
        if(!enabled)
            return;
        String factionName = hcfCore.getFactionName(player);
        double dtr = hcfCore.getFactionDTR(player);
        List<String> tags = new ArrayList<>();
        tags.add(AeroClientAPI.getConfigManager().nameTag.get(0)
                .replace("%faction_name%", factionName)
                .replace("%faction_dtr%", dtr +""));
        PacketNametagsOverride nametagsOverride = new PacketNametagsOverride(player.getUniqueId(), tags);
        for(Player user : Bukkit.getOnlinePlayers())
            nametagsOverride.setTo(user).sendPacket();
    }

    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AeroClientAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers())
                    update(player);
            }
        }, 10L, 1L);
    }
}
