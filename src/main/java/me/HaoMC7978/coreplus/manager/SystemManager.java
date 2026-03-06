package me.HaoMC7978.coreplus.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import me.HaoMC7978.coreplus.api.AbstractSystem;

public class SystemManager {
    private final Map<String, AbstractSystem> systems = new LinkedHashMap<>();

    /**
     * Đăng ký một hệ thống mới và kích hoạt nó.
     */
    public void register(AbstractSystem system) {
        systems.put(system.getId().toLowerCase(), system);
        system.enable();
    }

    /**
     * Lấy một System theo ID (trả về Optional để tránh lỗi NullPointerException).
     */
    public Optional<AbstractSystem> getSystem(String id) {
        return Optional.ofNullable(systems.get(id.toLowerCase()));
    }

    /**
     * Bật một hệ thống cụ thể.
     */
    public void enableSystem(String id) {
        getSystem(id).ifPresent(AbstractSystem::enable);
    }

    /**
     * Tắt một hệ thống cụ thể.
     */
    public void disableSystem(String id) {
        getSystem(id).ifPresent(AbstractSystem::disable);
    }

    /**
     * Tải lại (tắt rồi bật lại) một hệ thống cụ thể.
     */
    public void reloadSystem(String id) {
        getSystem(id).ifPresent(s -> {
            s.disable();
            s.enable();
        });
    }

    /**
     * Lấy danh sách ID của tất cả các hệ thống đã đăng ký.
     * HÀM NÀY SẼ SỬA LỖI [ERROR] TRONG SystemCmd.java
     */
    public List<String> getRegisteredSystemIds() {
        return new ArrayList<>(systems.keySet());
    }

    /**
     * Tắt toàn bộ hệ thống khi plugin bị disable.
     */
    public void disableAll() {
        systems.values().forEach(AbstractSystem::disable);
        systems.clear();
    }
}