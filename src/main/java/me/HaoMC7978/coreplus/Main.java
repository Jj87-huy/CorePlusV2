package me.HaoMC7978.coreplus;

import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import me.HaoMC7978.coreplus.api.AbstractSystem;
import me.HaoMC7978.coreplus.manager.SystemManager;

public final class Main extends JavaPlugin {
    private static Main instance;
    private SystemManager systemManager;

    @Override
    public void onEnable() {
        instance = this;
        this.systemManager = new SystemManager();

        // TỰ ĐỘNG QUÉT VÀ ĐĂNG KÝ
        try {
            // Quét tất cả các class trong package 'systems'
            Reflections reflections = new Reflections("me.HaoMC7978.coreplus.systems");
            Set<Class<? extends AbstractSystem>> classes = reflections.getSubTypesOf(AbstractSystem.class);

            for (Class<? extends AbstractSystem> clazz : classes) {
                // Chỉ lấy các class thực thi trực tiếp (không lấy abstract class).
                if (!java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                    AbstractSystem system = clazz.getDeclaredConstructor().newInstance();
                    systemManager.register(system);
                }
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            getLogger().severe("Error while automatically registering system: " + e.getMessage());
        }

        getLogger().info("CorePlusv2 đã tự động kích hoạt tất cả các hệ thống!");
    }

    @Override
    public void onDisable() {
        if (systemManager != null) systemManager.disableAll();
    }

    public static Main getInstance() { return instance; }
}