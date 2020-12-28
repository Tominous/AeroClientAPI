package net.aeroclient.aeroclientapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PayloadListener implements PluginMessageListener {
  public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
    System.out.println(s);
  }
}
