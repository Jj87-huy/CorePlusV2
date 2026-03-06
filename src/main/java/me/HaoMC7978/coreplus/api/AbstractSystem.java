package me.HaoMC7978.coreplus.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import me.HaoMC7978.coreplus.Main;

public abstract class AbstractSystem {
    private final String id;
    private final List<AbstractSystem> subSystems = new ArrayList<>();

    public AbstractSystem(String id) {
        this.id = id;
    }

    // Các hàm vòng đời
    public abstract void onEnable();
    public abstract void onDisable();

    // Hỗ trợ cấu trúc "thư mục con" (System lồng trong System)
    public void addSubSystem(AbstractSystem system) {
        subSystems.add(system);
    }

    public void enable() {
        onEnable();
        subSystems.forEach(AbstractSystem::enable);
    }

    public void disable() {
        subSystems.forEach(AbstractSystem::disable);
        onDisable();
    }

    // Công cụ đăng ký nhanh (Stable Registration)
    protected void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
    }

    protected void registerCommand(String name, CommandExecutor executor) {
        var cmd = Main.getInstance().getCommand(name);
        if (cmd != null) {
            cmd.setExecutor(executor);
            if (executor instanceof TabCompleter tabCompleter) {
                cmd.setTabCompleter(tabCompleter);
            }
        }
    }

    public String getId() { return id; }
}