package net.aeroclient.aeroclientapi.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class APIInfoCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    } 
    Player p = (Player)sender;
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    p.sendMessage(ChatColor.BLUE + "Version: " + ChatColor.GREEN + "1.3");
    p.sendMessage(ChatColor.BLUE + "Support: " + ChatColor.GREEN + "support.aeroclient.tk");
    p.sendMessage(ChatColor.BLUE + "Help: " + ChatColor.GREEN + "/APIhelp");
    p.sendMessage(ChatColor.BLUE + "Client Information: " + ChatColor.GREEN + "/client");
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    return true;
  }
}
