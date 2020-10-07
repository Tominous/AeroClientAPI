package cf.cicigames.aeroclientapi.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClientUnbanCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
    if(sender instanceof Player) {
      Player player = (Player) sender;
      if (args.length == 0)
        player.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "Please specify a player to unban.");
    }
    target.setBanned(false);
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[AAC]: " + target.getName() + " has been Aero Client Unbanned");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Reason: AAC Approved");
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    return true;
  }
}
