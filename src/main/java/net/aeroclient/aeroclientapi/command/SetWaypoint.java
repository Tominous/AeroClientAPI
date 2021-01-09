package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.packets.PacketWaypointAdd;
import net.aeroclient.aeroclientapi.utils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.awt.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWaypoint implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    }
    Player player = (Player) sender;
    if(args.length < 4) {
      player.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Usage: /setwaypoint <name> R G B ");
      return false;
    }
    String name = args[0];
    int r = 0;
    int g = 0;
    int b = 0;

    if(check(args[1])) {
      r = Integer.getInteger(args[1]);
    } else
      player.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Usage: /setwaypoint <name> R G B ");
    if(check(args[2])) {
      g = Integer.getInteger(args[2]);
    } else
      player.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Usage: /setwaypoint <name> R G B ");
    if(check(args[3])) {
      b = Integer.getInteger(args[3]);
    } else
      player.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Usage: /setwaypoint <name> R G B ");
    Color rgb = new Color(r, g, b);
    int color = rgb.getRGB();

    player.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Created Waypoint with name {name}".replace("{name}", name));
    PacketWaypointAdd waypointAdd = new PacketWaypointAdd(name, player.getLocation().getWorld().getName(), color,
            player.getLocation().getBlockX(),
            player.getLocation().getBlockY(),
            player.getLocation().getBlockZ(), true, true);
    for(Player users : Bukkit.getOnlinePlayers())
      waypointAdd.setTo(users).sendPacket();
    return false;
  }


  private boolean check(String input) {
    if(ReflectionUtil.isInt(input)) {
      return true;
    } else {
      return false;
    }
  }
}
