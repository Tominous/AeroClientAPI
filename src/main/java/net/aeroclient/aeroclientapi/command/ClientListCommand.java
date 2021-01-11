package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.AeroClientAPI;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClientListCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    } 
    Player p = (Player)sender;
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    p.sendMessage(ChatColor.BLUE + "§lAero Client" + ChatColor.WHITE + " Users");
    Iterator<UUID> var6 = AeroClientAPI.getPlayerManager().getOnAeroClient().iterator();
    while (var6.hasNext()) {
      UUID check = var6.next();
      Player target = Bukkit.getPlayer(check);
      if (target != null)
        p.sendMessage(ChatColor.BLUE + "§l - " + ChatColor.WHITE + target.getName());
    } 
    p.sendMessage("");
    p.sendMessage(ChatColor.AQUA + "§lLunar Client" + ChatColor.WHITE + " Users");
    var6 = AeroClientAPI.getPlayerManager().getOnLC().iterator();
    while (var6.hasNext()) {
      UUID check = var6.next();
      Player target = Bukkit.getPlayer(check);
      if (target != null)
        p.sendMessage(ChatColor.AQUA + "§l - " + ChatColor.WHITE + target.getName());
    } 
    p.sendMessage("");
    p.sendMessage(ChatColor.RED + "§lCheatBreaker" + ChatColor.WHITE + " Users");
    var6 = AeroClientAPI.getPlayerManager().getOnCheatBreaker().iterator();
    while (var6.hasNext()) {
      UUID check = var6.next();
      Player target = Bukkit.getPlayer(check);
      if (target != null)
        p.sendMessage(ChatColor.RED + "§l - " + ChatColor.WHITE + target.getName());
    }
    p.sendMessage("");
    p.sendMessage(ChatColor.STRIKETHROUGH + "---------------------------------");
    p.sendMessage("");
    return true;
  }
}
