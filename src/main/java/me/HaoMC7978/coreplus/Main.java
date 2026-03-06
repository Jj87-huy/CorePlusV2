package me.HaoMC7978.coreplus;

import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import me.HaoMC7978.coreplus.api.AbstractSystem;
import me.HaoMC7978.coreplus.commands.SystemCmd;
import me.HaoMC7978.coreplus.manager.SystemManager;

public final class Main extends JavaPlugin {
    private static Main instance;
    private SystemManager systemManager;

    @Override
    public void onEnable() {
        instance = this;
        this.systemManager = new SystemManager();

        // 1. TỰ ĐỘNG QUÉT VÀ ĐĂNG KÝ CÁC SYSTEM
        try {
            // Quét tất cả các class kế thừa AbstractSystem trong package 'systems'
            Reflections reflections = new Reflections("me.HaoMC7978.coreplus.systems");
            Set<Class<? extends AbstractSystem>> classes = reflections.getSubTypesOf(AbstractSystem.class);

            for (Class<? extends AbstractSystem> clazz : classes) {
                // Chỉ khởi tạo các class thực thi (không lấy abstract class)
                if (!java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                    AbstractSystem system = clazz.getDeclaredConstructor().newInstance();
                    systemManager.register(system);
                }
            }
        } catch (Exception e) {
            getLogger().severe("Lỗi khi tự động kích hoạt hệ thống: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. ĐĂNG KÝ LỆNH QUẢN LÝ /SYSTEM
        if (getCommand("system") != null) {
            SystemCmd systemCmd = new SystemCmd();
            getCommand("system").setExecutor(systemCmd);
            getCommand("system").setTabCompleter(systemCmd);
        }

        getLogger().info("CorePlusv2 đã khởi chạy và tự động kích hoạt các hệ thống!");
    }

    @Override
    public void onDisable() {
        // Tắt tất cả hệ thống khi server đóng để tránh rò rỉ dữ liệu
        if (systemManager != null) {
            systemManager.disableAll();
        }
    }

    // Getter để các class khác (như SystemCmd) truy cập vào Manager
    public SystemManager getSystemManager() {
        return this.systemManager;
    }

    public static Main getInstance() {
        return instance;
    }
}