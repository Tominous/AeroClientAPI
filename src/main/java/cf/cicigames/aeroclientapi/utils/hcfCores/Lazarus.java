package cf.cicigames.aeroclientapi.utils.hcfCores;

import org.bukkit.entity.Player;

public class Lazarus implements HCFCoreHandler {
    @Override
    public String getFactionName(Player player) {
        return "Unknown";
    }

    @Override
    public double getFactionDTR(Player name) {
        return 0;
    }
}
