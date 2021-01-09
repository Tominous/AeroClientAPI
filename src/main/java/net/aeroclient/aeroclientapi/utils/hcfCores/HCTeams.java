package net.aeroclient.aeroclientapi.utils.hcfCores;

import net.frozenorb.foxtrot.Foxtrot;
import org.bukkit.entity.Player;

public class HCTeams implements HCFCoreHandler {
    @Override
    public String getFactionName(Player player) {

        return Foxtrot.getInstance().getTeamHandler().getTeam((Player) player).getName();
    }

    @Override
    public double getFactionDTR(Player name) {
        return Foxtrot.getInstance().getTeamHandler().getTeam((Player) name).getDTR();
    }
}
