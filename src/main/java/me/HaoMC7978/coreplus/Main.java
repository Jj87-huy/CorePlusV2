package me.HaoMC7978.coreplus;

import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

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
        
        // Khởi tạo manager trước để sẵn sàng nhận đăng ký system
        this.systemManager = new SystemManager();

        // 1. TỰ ĐỘNG QUÉT VÀ KÍCH HOẠT CÁC SYSTEM NGAY LẬP TỨC
        // Hàm này sẽ gọi system.enable(), từ đó chạy logic check update lần đầu.
        loadSystems();

        // 2. ĐĂNG KÝ LỆNH /SYSTEM
        if (getCommand("system") != null) {
            SystemCmd systemCmd = new SystemCmd();
            getCommand("system").setExecutor(systemCmd);
            getCommand("system").setTabCompleter(systemCmd);
        }

        // 3. ĐĂNG KÝ LỆNH /CORE
        if (getCommand("core") != null) {
            CoreCmd coreCmd = new CoreCmd();
            getCommand("core").setExecutor(coreCmd);
            getCommand("core").setTabCompleter(coreCmd);
        }

        getLogger().info("§aCorePlusv2 đã khởi chạy thành công!");
    }

    /**
     * Quét và khởi tạo các module hệ thống.
     * Sử dụng ConfigurationBuilder để fix lỗi quét class sau khi Relocate/Shade.
     */
    private void loadSystems() {
        try {
            // Chỉ định rõ ràng ClassLoader của Plugin để Reflections tìm thấy class trong file JAR
            ConfigurationBuilder config = new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage("me.HaoMC7978.coreplus.systems", getClassLoader()))
                .addClassLoaders(getClassLoader())
                .setScanners(Scanners.SubTypes);

            Reflections reflections = new Reflections(config);
            
            // Tìm tất cả các class con của AbstractSystem trong package đã định nghĩa
            Set<Class<? extends AbstractSystem>> classes = reflections.getSubTypesOf(AbstractSystem.class);

            getLogger().info("§e[CorePlus] Đang quét và kích hoạt các module...");
            
            int count = 0;
            for (Class<? extends AbstractSystem> clazz : classes) {
                // Chỉ lấy các class cụ thể, bỏ qua abstract class hoặc interface
                if (!java.lang.reflect.Modifier.isAbstract(clazz.getModifiers()) && !clazz.isInterface()) {
                    AbstractSystem system = clazz.getDeclaredConstructor().newInstance();
                    
                    // Đăng ký vào manager và kích hoạt (chạy onEnable của system đó)
                    systemManager.register(system);
                    
                    getLogger().info("§f - Hệ thống đã sẵn sàng: §b" + system.getId());
                    count++;
                }
            }
            
            getLogger().info("§a[CorePlus] Hoàn tất tải §e" + count + " §amodules.");
            
        } catch (Exception e) {
            getLogger().severe("§cLỗi nghiêm trọng khi quét module hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        getLogger().info("§eĐã tải lại cấu hình config.yml!");
    }

    @Override
    public void onDisable() {
        // Tắt toàn bộ hệ thống để giải phóng tài nguyên (cancel task, close connection...)
        if (systemManager != null) {
            systemManager.disableAll();
        }
    }

    public SystemManager getSystemManager() {
        return this.systemManager;
    }

    public static Main getInstance() {
        return instance;
    }
}