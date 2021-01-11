package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AeroClientCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    } 
    Player p = (Player)sender;
    if(args.length != 0) {
      if(Bukkit.getPlayer(args[0]) != null) {
        p.sendMessage(ChatColor.GREEN + Bukkit.getPlayer(args[0]).getName() + " is on " + StringUtils.capitalize(AeroClientAPI.getPlayerManager().getClient(Bukkit.getPlayer(args[0]).getUniqueId()).getName().toLowerCase()));
        return false;
      } else {
        p.sendMessage(ChatColor.RED + "Unknown player " + args[0]);
        return false;
      }
    }
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    p.sendMessage(ChatColor.BLUE + "Aero Client " + ChatColor.GREEN + "is a modpack & client-side anticheat");
    p.sendMessage(ChatColor.BLUE + "Supported Versions: " + ChatColor.GREEN + "1.7.10");
    p.sendMessage(ChatColor.BLUE + "Website: " + ChatColor.GREEN + "https://aeroclient.net");
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    return true;
  }
}
