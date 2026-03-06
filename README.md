# 📦 CorePlusv2 - Modular Minecraft System

**CorePlusv2** là một framework mạnh mẽ dành cho server Minecraft (PaperMC), được thiết kế với kiến trúc **Modular (Viên chức hóa)**. Hệ thống cho phép tự động nhận diện và quản lý các tính năng (Systems) mà không cần can thiệp vào mã nguồn chính.

---

## ✨ Tính năng nổi bật

* **Auto-Registration**: Tự động quét và đăng ký các System trong package thông qua thư viện `Reflections`.
* **Dynamic Management**: Bật, tắt hoặc tải lại (reload) từng module ngay trong game mà không cần restart server.
* **Command System**: Tích hợp sẵn bộ lệnh `/core` và `/system` với tính năng Tab-Complete thông minh.
* **Developer Friendly**: Cấu trúc code rõ ràng, sử dụng Maven để quản lý dependencies.

---

## 🛠 Yêu cầu hệ thống

* **Java**: 17 trở lên.
* **Server**: Paper/Spigot 1.20+.
* **Dependencies**:
* PlaceholderAPI (Bắt buộc).
* Vault & LuckPerms (Hỗ trợ thêm).



---

## 📥 Cách cài đặt & Biên dịch

1. Clone project về máy hoặc mở trong VS Code.
2. Đảm bảo đã cài đặt Maven.
3. Chạy lệnh biên dịch:
```bash
mvn clean package

```


4. Lấy file `.jar` trong thư mục `target` và bỏ vào folder `plugins` của server.

---

## 🎮 Cách sử dụng (Commands)

| Lệnh | Mô tả | Quyền hạn |
| --- | --- | --- |
| `/core reload` | Tải lại toàn bộ cấu hình `config.yml`. | `coreplus.admin` |
| `/system enable <id>` | Kích hoạt một module cụ thể. | `coreplus.admin` |
| `/system disable <id>` | Tắt một module cụ thể. | `coreplus.admin` |
| `/system reload <id>` | Khởi động lại một module nhanh chóng. | `coreplus.admin` |

---

## 💻 Hướng dẫn thêm Code (Developer Guide)

Bạn không cần sửa file `Main.java`. Để thêm một tính năng mới, chỉ cần tạo một Class mới trong package `me.HaoMC7978.coreplus.systems`.

### Code mẫu cho một System mới:

Tạo file: `src/main/java/me/HaoMC7978/coreplus/systems/chat/ChatSystem.java`

```java
package me.HaoMC7978.coreplus.systems.chat;

import me.HaoMC7978.coreplus.api.AbstractSystem;
import org.bukkit.Bukkit;

public class ChatSystem extends AbstractSystem {

    @Override
    public String getId() {
        return "Chat"; // ID dùng để điều khiển qua lệnh /system
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[ChatSystem] Đã được kích hoạt tự động!");
        // Đăng ký Listener hoặc Task tại đây
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[ChatSystem] Đã ngừng hoạt động!");
        // Hủy đăng ký hoặc lưu dữ liệu tại đây
    }
}

```

---

## 📂 Cấu trúc thư mục dự án

* `api/`: Chứa các Interface và Abstract class cốt lõi.
* `manager/`: Chứa các bộ quản lý (SystemManager, DatabaseManager).
* `commands/`: Chứa logic xử lý các lệnh của plugin.
* `systems/`: Nơi chứa tất cả các module tính năng của bạn.

---

## 🤝 Đóng góp & Hỗ trợ

Nếu bạn gặp lỗi hoặc muốn đóng góp ý tưởng:

1. Tạo **Issue** trên GitHub.
2. Liên hệ trực tiếp qua Discord cá nhân.

---

## 📄 License
Dự án này được cấp phép theo **MIT License** - xem file [LICENSE](LICENSE) để biết thêm chi tiết.

**CorePlusv2** - Phát triển bởi **HaoMC7978**.
