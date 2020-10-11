package cf.cicigames.aeroclientapi.command;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BanwaveCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "Usage: /banwave <start>");
      return true;
    }
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Aero Client Banwave Starting");
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    AeroClientAPI.getBanWaveManager().startBanWave();
 //   Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
 //   Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Aero Client Banwaves are currently disabled.");
   // Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    return false;
  }
}
