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
    } else if (uuid.equalsIgnoreCase("58025126-17c2-4fe3-bbc9-81519341f7d6") || uuid.equalsIgnoreCase("12e0d63d-9e50-49a9-b5fe-4229fba667f2") || uuid.equalsIgnoreCase("285c25e3-74f6-47e0-81a6-4e74ceb54ed3")) {
      sender.sendMessage(ChatColor.BLUE + "[API]: " + ChatColor.RED + "You may not ban that player.");
      return false;
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
