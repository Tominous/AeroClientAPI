package cf.cicigames.aeroclientapi.command;

import cf.cicigames.aeroclientapi.enums.Notification;
import cf.cicigames.aeroclientapi.enums.StaffModule;
import cf.cicigames.aeroclientapi.packets.PacketCooldown;
import cf.cicigames.aeroclientapi.packets.PacketNotification;
import cf.cicigames.aeroclientapi.packets.PacketStaffMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModsCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (args.length == 0) {
      (new PacketNotification(Notification.INFO, "Enabled Staff Modules ", 5000)).setTo((Player) sender).sendPacket();

      (new PacketStaffMod(StaffModule.XRAY, true)).setTo((Player) sender).sendPacket();
      (new PacketStaffMod(StaffModule.BUNNYHOP, true)).setTo((Player) sender).sendPacket();
      (new PacketStaffMod(StaffModule.NAMETAGS, true)).setTo((Player) sender).sendPacket();
      sender.sendMessage(ChatColor.BLUE + "[AAC]: Staff Modules have been enabled.");

      return true;
    }
    Player target = Bukkit.getServer().getPlayer(args[0]);

    if (target == null) {
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "No player is online with that name.");
      return true;
    } 
    sender.sendMessage(ChatColor.BLUE + "[AAC]: Staff Modules have been enabled for " + target.getName() + ".");
    target.sendMessage(ChatColor.BLUE + "[AAC]: Staff Modules have been enabled.");
    (new PacketNotification(Notification.INFO, "Enabled Staff Modules ", 5000)).setTo(target).sendPacket();

    (new PacketStaffMod(StaffModule.XRAY, true)).setTo(target).sendPacket();
      (new PacketStaffMod(StaffModule.BUNNYHOP, true)).setTo(target).sendPacket();
      (new PacketStaffMod(StaffModule.NAMETAGS, true)).setTo(target).sendPacket();//
      (new PacketStaffMod(StaffModule.NOCLIP, true)).setTo(target).sendPacket();//


    return false;
  }
}
