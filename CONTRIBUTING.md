# 🤝 Hướng dẫn đóng góp cho CorePlusv2

Cảm ơn bạn đã quan tâm đến việc đóng góp cho **CorePlusv2**! Để đảm bảo dự án luôn sạch sẽ và hoạt động ổn định, vui lòng tuân thủ các quy tắc dưới đây.

---

## 🚀 Quy trình đóng góp nhanh
1. **Fork** dự án về tài khoản của bạn.
2. Tạo một **Branch** mới cho tính năng của bạn (`git checkout -b feature/AmazingFeature`).
3. **Commit** các thay đổi của bạn với mô tả rõ ràng.
4. **Push** lên branch của bạn (`git push origin feature/AmazingFeature`).
5. Mở một **Pull Request** để mình kiểm duyệt.

---

## 💻 Quy chuẩn viết Code
Để giữ cho hệ thống Modular hoạt động tốt, vui lòng tuân thủ:

### 1. Cấu trúc Package
* Tất cả các tính năng mới (System) phải nằm trong package: `me.HaoMC7978.coreplus.systems.<tên_tính_năng>`.
* Các class xử lý lệnh phải nằm trong: `me.HaoMC7978.coreplus.commands`.

### 2. Định dạng Code
* Sử dụng **Java 17**.
* Tên class theo chuẩn `PascalCase` (Ví dụ: `CombatSystem.java`).
* Luôn thêm `@Override` khi ghi đè các phương thức từ `AbstractSystem`.

### 3. Nguyên tắc Modular
* Mỗi System phải có một ID duy nhất và không trùng lặp.
* Không viết logic chồng chéo giữa các System. Sử dụng API nếu cần giao tiếp giữa các module.
* Luôn dọn dẹp (unregister listener, cancel task) trong phương thức `onDisable()`.

---

## 🛠 Cách kiểm tra (Testing)
Trước khi gửi đóng góp, hãy đảm bảo plugin có thể biên dịch thành công mà không có lỗi:

```bash
mvn clean package

```

Kiểm tra file `.jar` trong thư mục `target` và chạy thử trên server local để xác nhận:

1. Module mới được tự động nhận diện.
2. Lệnh `/system reload <id>` hoạt động chính xác.
3. Không gây rò rỉ bộ nhớ (Memory Leak).

---

## 📝 Báo cáo lỗi (Issue)

Nếu bạn tìm thấy lỗi, vui lòng tạo một **Issue** với các thông tin sau:

* Phiên bản PaperMC đang dùng.
* Log lỗi từ Console (nếu có).
* Các bước để tái hiện lỗi.

---

Cảm ơn sự đóng góp của bạn! Chúc bạn coding vui vẻ! 👨‍💻