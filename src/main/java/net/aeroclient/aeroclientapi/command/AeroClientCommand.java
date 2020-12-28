package net.aeroclient.aeroclientapi.command;

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
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    p.sendMessage(ChatColor.BLUE + "Aero Client is a " + ChatColor.GREEN + "modpack & client-side anticheat.");
    p.sendMessage(ChatColor.BLUE + "Supported Versions: " + ChatColor.GREEN + "1.7.10");
    p.sendMessage(ChatColor.BLUE + "Website: " + ChatColor.GREEN + "aeroclient.tk");
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    return true;
  }
}
