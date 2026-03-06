package me.HaoMC7978.coreplus.manager;

import me.HaoMC7978.coreplus.api.AbstractSystem;
import java.util.LinkedHashMap;
import java.util.Map;

public class SystemManager {
    private final Map<String, AbstractSystem> systems = new LinkedHashMap<>();

    public void register(AbstractSystem system) {
        systems.put(system.getId().toLowerCase(), system);
        system.enable();
    }

    public void disableAll() {
        // Tắt theo thứ tự ngược lại để đảm bảo an toàn dữ liệu
        systems.values().forEach(AbstractSystem::disable);
        systems.clear();
    }
}