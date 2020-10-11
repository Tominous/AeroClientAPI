package cf.cicigames.aeroclientapi.command;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import cf.cicigames.aeroclientapi.utils.ClientPacket;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotConfig;

public class ClientBanCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "Please specify a player to ban.");
      return true;
    } 
    Player target = Bukkit.getServer().getPlayer(args[0]);
    if (target == null) {
      sender.sendMessage(ChatColor.RED + "No player is online with that name.");
      return true;
    } 
    if (target.getUniqueId().toString().equalsIgnoreCase("58025126-17c2-4fe3-bbc9-81519341f7d6" )) { //Change it to UUID not IGN
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "You may not ban that player.");
      return false;
    }
    if(!SpigotConfig.bungee && !MinecraftServer.getServer().getOnlineMode()) {
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "You may not ban that player while server is not using online mode");

    }
    if(AeroClientAPI.getPlayerManager().getClient(target.getUniqueId()) != ClientPacket.Client.AEROCLIENT) {
      sender.sendMessage(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "That player is not on Aero Client.");
      return false;
    }
    target.kickPlayer(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "You have been Aero Client banned.");
    target.setBanned(true);
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[AAC]: " + target.getName() + " has been Aero Client Banned");
    Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Reason: Cheating");
    Bukkit.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "-------------------------------------------");
    return false;
  }
}
