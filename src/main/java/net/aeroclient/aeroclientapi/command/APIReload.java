package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class APIReload implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
    sender.sendMessage(ChatColor.BLUE + "[API] " + ChatColor.GREEN + "Reloaded Config!");
    AeroClientAPI.getConfigManager().reload();
    return false;
  }
}
