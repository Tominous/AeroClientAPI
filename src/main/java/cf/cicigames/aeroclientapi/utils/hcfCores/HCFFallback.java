package cf.cicigames.aeroclientapi.utils.hcfCores;

import net.frozenorb.foxtrot.Foxtrot;
import org.bukkit.entity.Player;

public class HCFFallback implements HCFCoreHandler {
    @Override
    public String getFactionName(Player player) {
        return  "Unknown";
    }

    @Override
    public double getFactionDTR(Player name) {
        return 0.0;//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    }
}
