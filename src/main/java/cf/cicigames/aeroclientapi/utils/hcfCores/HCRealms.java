package cf.cicigames.aeroclientapi.utils.hcfCores;
import org.bukkit.entity.Player;
import us.hcrealms.hcf.Base;

public class HCRealms implements HCFCoreHandler {

    @Override
    public String getFactionName(Player player) {
        return Base.getInstance().getFactionManager().getPlayerFaction(player.getUniqueId()).getName();
    }

    @Override
    public double getFactionDTR(Player name) {
        return Base.getInstance().getFactionManager().getPlayerFaction(name).getDeathsUntilRaidable();
    }
}
