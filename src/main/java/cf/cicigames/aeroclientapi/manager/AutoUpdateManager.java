package cf.cicigames.aeroclientapi.manager;

import cf.cicigames.aeroclientapi.AeroClientAPI;
import com.google.gson.*;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

public class AutoUpdateManager {
    private final String version = "1.6";

    private final String GET_RELEASES_URL = "https://api.github.com/repos/Aero-Client/AeroClientAPI/releases";

    public void checkForUpdate() throws IOException {
        AeroClientAPI.getInstance().getLogger().info("Checking for updates.");
        URL url = new URL(GET_RELEASES_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JsonObject object = new JsonParser().parse(reader).getAsJsonArray().get(0).getAsJsonObject();
        String version = object.get("tag_name").getAsString();
        String author = object.get("author").getAsJsonObject().get("login").getAsString();
        String downloadURL = object.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString();

        if (version.equals(this.version)) {
            AeroClientAPI.getInstance().getLogger().info("No updates found.");
            return;
        } else if (version.contains("-dev") || version.contains("-development")) {
            AeroClientAPI.getInstance().getLogger().info("Found new update but its a development build.");
            return;
        }  // servers should always upgrade from development builds, not stay at them since development build can have problems etc.
        foundUpdate(version, author, downloadURL);
    }

    public void foundUpdate(String version, String author, String downloadURL) {
        downloadFile(downloadURL, version);

        AeroClientAPI.getInstance().getLogger().info("Update Date Checker Found update with version " + version + " your version is " + this.version + ". This update was made by " + author);
        AeroClientAPI.getInstance().getLogger().info("Please restart your server to use this new version");
        Bukkit.broadcast(ChatColor.BLUE + "[AAC] Update Date Checker Found update with version " + version + " your version is " + this.version + ". This update was made by " + author, "AeroClientAPI.admin");
        Bukkit.broadcast(ChatColor.BLUE + "[AAC] Please restart your server to use this new version","AeroClientAPI.admin");
    }


    private void downloadFile(String urlF, String version) {
        AeroClientAPI.getNmsHandler().downloadFile(urlF, "AeroClientAPI-" + version +".jar");
        File file = new File(AeroClientAPI.getInstance().getDataFolder().getParent() + "/AeroClientAPI-" + version + ".jar");
        file.delete();
        AeroClientAPI.getInstance().getLogger().info("Downloaded File");
    }

}
