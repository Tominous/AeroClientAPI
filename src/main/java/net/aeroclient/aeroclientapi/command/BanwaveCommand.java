package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanwaveCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "Usage: /banwave <start>");
      return true;
    }
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "An Aero Client Banwave is Starting");
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    AeroClientAPI.getBanWaveManager().startBanWave();
    return false;
  }
}
