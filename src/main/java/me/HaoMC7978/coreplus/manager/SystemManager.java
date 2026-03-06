package me.HaoMC7978.coreplus.manager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import me.HaoMC7978.coreplus.api.AbstractSystem;

public class SystemManager {
    private final Map<String, AbstractSystem> systems = new LinkedHashMap<>();

    public void register(AbstractSystem system) {
        systems.put(system.getId().toLowerCase(), system);
        system.enable();
    }

    // Lấy một System theo ID
    public Optional<AbstractSystem> getSystem(String id) {
        return Optional.ofNullable(systems.get(id.toLowerCase()));
    }

    // Bật lại một System
    public void enableSystem(String id) {
        getSystem(id).ifPresent(AbstractSystem::enable);
    }

    // Tắt một System
    public void disableSystem(String id) {
        getSystem(id).ifPresent(AbstractSystem::disable);
    }

    // Reload một System
    public void reloadSystem(String id) {
        getSystem(id).ifPresent(s -> {
            s.disable();
            s.enable();
        });
    }

    public void disableAll() {
        systems.values().forEach(AbstractSystem::disable);
        systems.clear();
    }
}