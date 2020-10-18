package cf.cicigames.aeroclientapi.listeners;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import cf.cicigames.aeroclientapi.enums.Notification;
import cf.cicigames.aeroclientapi.enums.ServerRule;
import cf.cicigames.aeroclientapi.enums.StaffModule;
import cf.cicigames.aeroclientapi.packets.*;

import cf.cicigames.aeroclientapi.utils.ClientPacket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
//
public class ClientLoginListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin) AeroClientAPI.getInstance(), () -> registerClient(p), 5L);
  }
  
  @EventHandler
  public void onDebugJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)AeroClientAPI.getInstance(), () -> {
      if(AeroClientAPI.getConfigManager().aeroClientOnly)
        if(!AeroClientAPI.getPlayerManager().getClient(p.getUniqueId()).equals(ClientPacket.Client.AEROCLIENT)) {
          e.setJoinMessage(null);
          p.kickPlayer(ChatColor.translateAlternateColorCodes('&', AeroClientAPI.getConfigManager().aeroClientOnlyMessage));
        }///v
        (new PacketServerRule(ServerRule.VOICE_ENABLED, true)).setTo(p).sendPacket();
          if (p.hasPermission("aeroclientapi.staffmods")) {

            (new PacketStaffMod(StaffModule.XRAY, true)).setTo(p).sendPacket();
            (new PacketStaffMod(StaffModule.BUNNYHOP, true)).setTo(p).sendPacket();
            (new PacketStaffMod(StaffModule.NAMETAGS, true)).setTo(p).sendPacket();
            (new PacketNotification(Notification.INFO, "Enabled Staff Modules ", 5000)).setTo(p).sendPacket();
          }

        },10L);
  }
  
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onProjectileLaunch(ProjectileLaunchEvent event) {
    if (event.getEntityType() == EntityType.ENDER_PEARL) {
      EnderPearl pearl = (EnderPearl)event.getEntity();
      if (pearl.getShooter() instanceof Player) {
        Player shooter = (Player)pearl.getShooter();
   //     (new PacketCooldown("enderpearl", 16000L, 368)).setTo(shooter).sendPacket();
      } 
    } 
  }
  
  @EventHandler
  public void onRegister(PlayerRegisterChannelEvent e) {
    if (e.getChannel().equals("AC-Client")) {
      AeroClientAPI.getPlayerManager().addToAC(e.getPlayer());
      AeroClientAPI.getVoiceChannelHandler().getMuteMap().put(e.getPlayer().getUniqueId(), new ArrayList());
      new PacketUpdateWorld(e.getPlayer().getWorld().getName()).setTo(e.getPlayer()).sendPacket();
    }
    if (e.getChannel().equals("Lunar-Client"))
      AeroClientAPI.getPlayerManager().addToLC(e.getPlayer());
  }
  
  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent e) {
    AeroClientAPI.getPlayerManager().removeFromAC(e.getPlayer());
    AeroClientAPI.getPlayerManager().removeFromLC(e.getPlayer());
    AeroClientAPI.getVoiceChannelHandler().getPlayerActiveChannels().remove(e.getPlayer().getUniqueId());
    AeroClientAPI.getVoiceChannelHandler().getMuteMap().remove(e.getPlayer().getUniqueId());
  }
  
  private void registerClient(Player p) {
    Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)AeroClientAPI.getInstance(), () -> {
          try {
            AeroClientAPI.getNmsHandler().registerChannel(p);
          } catch (Exception var4) {
            var4.printStackTrace();
          } 
        });
  }
}
