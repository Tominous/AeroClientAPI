package cf.cicigames.aeroclientapi.command;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import cf.cicigames.aeroclientapi.enums.Notification;
import cf.cicigames.aeroclientapi.packets.PacketNotification;
import cf.cicigames.aeroclientapi.utils.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ANotification implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    }
    Player player = (Player) sender;
    if(args.length < 4)
      player.sendMessage(ChatColor.BLUE + "[AAC] " + ChatColor.GREEN + "Usage: /anotification <player / all> <info, error, neutral> <time> <message>");
    boolean everyone = args[0].equalsIgnoreCase("all");
    int time = 0;
    if(ReflectionUtil.isInt(args[2])) {
      time = Integer.valueOf(args[2]);
    } else
      player.sendMessage(ChatColor.BLUE + "[AAC] " + ChatColor.GREEN + "Usage: /anotification <player / all> <info, error, neutral> <time> <message>");
    if(!args[0].equalsIgnoreCase("all")) {
      if(Bukkit.getPlayer(args[0]) == null) {
        player.sendMessage(ChatColor.BLUE + "[AAC] " + ChatColor.GREEN + "That Player is not online");
        return false;
      }
    }
    if(!enumExists(args[1])) {
      player.sendMessage(ChatColor.BLUE + "[AAC] " + ChatColor.GREEN + "Usage: /anotification <player / all> <info, error, neutral> <time> <message>");
      return false;
    }
    Notification noti = Notification.valueOf(args[1].toUpperCase());
    String a = everyone ? "Everyone on AeroClient" : args[0];
    player.sendMessage(ChatColor.BLUE + "[AAC] " + ChatColor.GREEN + "Sent a Notifications to " + a);
    PacketNotification notification = new PacketNotification(noti, message(args), time);
    if(everyone) {
      for(Player player1 : Bukkit.getOnlinePlayers())
        notification.setTo(player1).sendPacket();
    } else {
      notification.setTo(Bukkit.getPlayer(args[0])).sendPacket();
    }
    return false;
  }


  private boolean enumExists(String enumI) {
    for (Notification me : Notification.values()) {
      if (me.name().equalsIgnoreCase(enumI))
        return true;
    }
    return false;
  }

  private String message(String[] input) {
    String[] values = null;
    StringBuilder sb = new StringBuilder();
    for (String s : input) {
      if(sb.length() != 0)
        sb.append(" ");
      sb.append(s);
    }
    return StringUtils.substring(sb.toString(), 3);

  }

}
