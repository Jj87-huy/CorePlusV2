package me.HaoMC7978.coreplus;

import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import me.HaoMC7978.coreplus.api.AbstractSystem;
import me.HaoMC7978.coreplus.commands.CoreCmd;
import me.HaoMC7978.coreplus.commands.SystemCmd;
import me.HaoMC7978.coreplus.manager.SystemManager;

public final class Main extends JavaPlugin {
    private static Main instance;
    private SystemManager systemManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.systemManager = new SystemManager();

        // 1. TỰ ĐỘNG QUÉT VÀ ĐĂNG KÝ CÁC SYSTEM
        loadSystems();

        // 2. ĐĂNG KÝ LỆNH /SYSTEM (Khớp với plugin.yml)
        if (getCommand("system") != null) {
            SystemCmd systemCmd = new SystemCmd();
            getCommand("system").setExecutor(systemCmd);
            getCommand("system").setTabCompleter(systemCmd);
        }

        // 3. ĐĂNG KÝ LỆNH /CORE (Khớp với plugin.yml)
        if (getCommand("core") != null) {
            CoreCmd coreCmd = new CoreCmd();
            getCommand("core").setExecutor(coreCmd);
            getCommand("core").setTabCompleter(coreCmd);
        }

        getLogger().info("CorePlusv2 đã khởi chạy thành công!");
    }

    private void loadSystems() {
        try {
            Reflections reflections = new Reflections("me.HaoMC7978.coreplus.systems");
            Set<Class<? extends AbstractSystem>> classes = reflections.getSubTypesOf(AbstractSystem.class);

            for (Class<? extends AbstractSystem> clazz : classes) {
                if (!java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                    AbstractSystem system = clazz.getDeclaredConstructor().newInstance();
                    systemManager.register(system);
                }
            }
        } catch (Exception e) {
            getLogger().severe("Lỗi khi tự động kích hoạt hệ thống: " + e.getMessage());
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        getLogger().info("Đã tải lại cấu hình CorePlusv2!");
    }

    @Override
    public void onDisable() {
        if (systemManager != null) systemManager.disableAll();
    }

    public SystemManager getSystemManager() { return this.systemManager; }
    public static Main getInstance() { return instance; }
}