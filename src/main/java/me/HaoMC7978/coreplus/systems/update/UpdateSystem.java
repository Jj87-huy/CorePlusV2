package me.HaoMC7978.coreplus.systems.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.HaoMC7978.coreplus.Main;
import me.HaoMC7978.coreplus.api.AbstractSystem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateSystem extends AbstractSystem {
    private final String UPDATE_URL = "https://raw.githubusercontent.com/Jj87-huy/CorePlusV2/main/update.json";
    private final OkHttpClient client = new OkHttpClient();
    private BukkitTask updateTask;

    public UpdateSystem() {
        super("Update");
    }

    @Override
    public String getId() {
        return "Update";
    }

    @Override
    public void onEnable() {
        // Chạy kiểm tra ngay khi bật plugin
        checkUpdate();

        // Lập lịch kiểm tra 12 tiếng một lần (12h * 60m * 60s * 20 ticks)
        long interval = 12 * 60 * 60 * 20L;
        updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this::checkUpdate, interval, interval);
    }

    @Override
    public void onDisable() {
        if (updateTask != null) {
            updateTask.cancel();
        }
    }

    private void checkUpdate() {
        Request request = new Request.Builder().url(UPDATE_URL).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) return;

            String jsonData = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();

            // Lấy key cuối cùng (mới nhất)
            List<String> keys = new ArrayList<>(jsonObject.keySet());
            if (keys.isEmpty()) return;
            
            String latestVersion = keys.get(keys.size() - 1);
            String currentVersion = Main.getInstance().getDescription().getVersion();

            if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                String downloadLink = jsonObject.getAsJsonObject(latestVersion).get("download").getAsString();
                
                Bukkit.getConsoleSender().sendMessage("§e[CorePlusV2] Một phiên bản mới đã có sẵn: §f" + latestVersion);
                Bukkit.getConsoleSender().sendMessage("§e[CorePlusV2] Phiên bản hiện tại: §7" + currentVersion);
                Bukkit.getConsoleSender().sendMessage("§e[CorePlusV2] Tải ngay tại: §b" + downloadLink);
            }

        } catch (IOException e) {
            Bukkit.getLogger().warning("[UpdateSystem] Không thể kết nối tới GitHub để kiểm tra cập nhật.");
        }
    }
}