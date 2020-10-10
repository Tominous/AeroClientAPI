package cf.cicigames.aeroclientapi.manager;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import com.google.gson.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

public class AutoUpdateManager {
    private String version = "1.6";

    private String URL = "https://api.github.com/repos/Aero-Client/AeroClientAPI/releases";

    public void checkforupdate() {
        System.out.println("Checking for Updates ");
        JsonObject jsonObject;
        jsonObject= getFromURL(URL).get(0).getAsJsonObject();
        String version = jsonObject.get("tag_name").getAsString();
        String author = jsonObject.get("author").getAsJsonObject().get("login").getAsString();
        String downloadURL = jsonObject.get("assets").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("browser_download_url").getAsString();
        if(version.equalsIgnoreCase(this.version) || Double.parseDouble(this.version.split("-")[0]) > Double.parseDouble(version)) {
            return;
        }
        if(version.contains("-dev") || version.contains("-development")) {
            System.out.println("Found new update but you are on a development version");
            return;
        }

        foundUpdate(version, author, downloadURL);

    }
    //if your version has -dev or -development in it no update will happen

    public void foundUpdate(String version, String author, String downloadURL) {
        downloadFile(downloadURL, version);

        AeroClientAPI.getInstance().getLogger().info("Update Date Checker Found update with version " + version + " your version is " + this.version + ". This update was made by " + author);
        AeroClientAPI.getInstance().getLogger().info("Please restart your server to use this new version");
        Bukkit.broadcast(ChatColor.BLUE + "[AAC] Update Date Checker Found update with version " + version + " your version is " + this.version + ". This update was made by " + author, "AeroClientAPI.admin");
        Bukkit.broadcast(ChatColor.BLUE + "[AAC] Please restart your server to use this new version","AeroClientAPI.admin");
    }




    @SneakyThrows
    private JsonArray getFromURL(String url) {
        URL banWave = new URL(url); // URL to Parse
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
        JsonArray jsonObj = element.getAsJsonArray();
        in.close();
        return jsonObj;
    }


    @SneakyThrows
    private void downloadFile(String urlF, String version) {
        AeroClientAPI.getNmsHandler().downloadFile(urlF, "AeroClientAPI-" + version +".jar");
        File file = new File(AeroClientAPI.getInstance().getDataFolder().getParent() + "/AeroClientAPI-" + version + ".jar");
        file.delete();
        AeroClientAPI.getInstance().getLogger().info("Downloaded File");
    }

}
