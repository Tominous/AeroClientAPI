package cf.cicigames.aeroclientapi.manager;


import com.google.gson.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;


public class BanWaveManager {
    private String BanWaveURL = "https://raw.githubusercontent.com/Aero-Client/ClientAPI/master/AeroClientAPI/banlist";
    public void startBanWave() {
        JsonObject jsonObject = getFromURL(BanWaveURL);
        Map<String, Object> attributes = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.get("banned").getAsJsonObject().entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            attributes.put(entry.getKey(), jsonObject.get(entry.getKey()));
        }
        for(Map.Entry<String,Object> att : attributes.entrySet()){
            banUser(att.getKey());
        }

    }


    @SneakyThrows
    private JsonObject getFromURL(String url) {
        URL banWave = new URL(BanWaveURL); // URL to Parse
        BufferedReader br = new BufferedReader(new InputStreamReader(banWave.openStream()));
        String jsonS = "";
        URLConnection conn = banWave.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while((inputLine = in.readLine()) != null) {
            jsonS+=inputLine;
        }
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (jsonS, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        in.close();
        return jsonObj;
    }

    private void banUser(String name) {
        Bukkit.broadcastMessage(ChatColor.BLUE + "[AAC]: " + name + " has been Aero Client banned.");
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if(offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().kickPlayer(ChatColor.BLUE + "[AAC]: " + ChatColor.RED + "You have been Aero Client banned.");
        }
        offlinePlayer.setBanned(true);
    }

}
