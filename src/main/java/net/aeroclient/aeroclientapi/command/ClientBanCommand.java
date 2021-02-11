package net.aeroclient.aeroclientapi.command;

import net.aeroclient.aeroclientapi.AeroClientAPI;
import net.aeroclient.aeroclientapi.utils.ClientPacket;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotConfig;

public class ClientBanCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be ran in-game.");
      return false;
    }
    Player target = Bukkit.getServer().getPlayer(args[0]);
    String uuid = target.getUniqueId().toString();
    if (args.length == 0) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "Please specify a player to ban.");
      return true;
    } else if (target == null) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "No player is online with that name.");
      return true;
    } else if(!SpigotConfig.bungee && !MinecraftServer.getServer().getOnlineMode()) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "You may not ban that player while the server is not using online mode.");
    } else if(AeroClientAPI.getPlayerManager().getClient(target.getUniqueId()) != ClientPacket.Client.AEROCLIENT) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "That player is not on Aero Client.");
      return false;
    }
    target.kickPlayer(ChatColor.RED + "You have been Aero Client banned.");
    target.setBanned(true);
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + target.getName() + " has been Aero Client Banned");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Reason: Cheating");
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    return false;
  }
}
