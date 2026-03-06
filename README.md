# 🚚 DeliverAcct System (DeliverAcctAutoAlert)

Một hệ thống quản lý vận hành Logistics và Kế toán kho (Warehouse Accounting) toàn diện, được xây dựng dựa trên mô hình MVC (Model-View-Controller) sử dụng Java Servlets và JSP.

Dự án này giúp quản lý xuyên suốt từ khâu tạo đơn hàng, điều phối giao nhận, nhập/xuất kho, cho đến đối soát thu hộ (COD) và cảnh báo rủi ro tự động.

## 🌟 Các tính năng chính (Modules)

Hệ thống được phân quyền chặt chẽ với các Dashboard riêng biệt cho từng nhóm người dùng (Role):

* **🛡️ Module Admin (Quản trị hệ thống)**
    * Quản lý Tài khoản (Users) & Phân quyền (Roles).
    * Ghi nhận nhật ký hoạt động hệ thống (Audit Logs).
    * Cấu hình luật cảnh báo rủi ro (Alert Rules).
* **📦 Module Master Data (Danh mục gốc)**
    * Quản lý danh sách Khách hàng, Sản phẩm, và Kho bãi (Warehouses).
* **🏍️ Module Delivery (Điều phối & Giao nhận)**
    * Tạo và quản lý đơn hàng (Orders).
    * Ghép chuyến và điều phối tài xế (Shipments).
    * Cập nhật trạng thái và upload bằng chứng giao hàng (POD - Proof of Delivery).
* **🏭 Module Warehouse (Kế toán Kho)**
    * Tạo phiếu Nhập kho / Xuất kho.
    * Theo dõi Sổ cái tồn kho (Stock Ledger) theo thời gian thực.
* **💰 Module Accounting (Kế toán & Đối soát)**
    * Quản lý Hóa đơn và Lịch sử thanh toán.
    * Bàn làm việc đối soát COD (Reconcile Workbench) và giải trình sai lệch.
* **🚨 Module Risk (Kiểm soát rủi ro)**
    * Theo dõi danh sách cảnh báo (Alerts) và tạo các Case xử lý ngoại lệ.

## 🛠️ Công nghệ sử dụng

* **Ngôn ngữ lập trình:** Java (JDK 8/11+)
* **Web Technologies:** Java Servlets, JSP (JavaServer Pages), JSTL
* **Kiến trúc:** MVC (Model - View - Controller), Front-Controller Pattern (`MainController`)
* **Cơ sở dữ liệu:** SQL Server / MySQL (Sử dụng JDBC để kết nối)
* **Giao diện:** HTML thuần (Tối ưu hóa tốc độ và tập trung vào luồng xử lý logic)
* **IDE:** Apache NetBeans / Eclipse
* **Web Server:** Apache Tomcat 8.5/9.0+

## 📂 Cấu trúc thư mục dự án (Architecture)

Dự án áp dụng chặt chẽ mô hình MVC, tách biệt logic nghiệp vụ, giao diện và tương tác cơ sở dữ liệu:

```text
DeliverAcctAutoAlert/
├── src/java/
│   ├── authentication/   # Chứa các model liên quan đến Auth (User, Role...)
│   ├── controller/       # Chứa MainController (Router) và các Controller nghiệp vụ
│   ├── dao/              # Data Access Object (Chứa các câu lệnh SQL truy vấn DB)
│   ├── model/            # Chứa các class Object (Customer, Product, Order...)
│   └── utils/            # Các class tiện ích (DBUtils kết nối database)
├── WebContent/ (hoặc Web Pages/)
│   ├── WEB-INF/          # Chứa cấu hình web.xml
│   ├── admin/            # Các trang JSP dành cho Admin (Dashboard, UserList, UserForm...)
│   ├── delivery/         # Các trang JSP cho nghiệp vụ giao nhận
│   ├── warehouse/        # Các trang JSP cho nghiệp vụ kho bãi
│   ├── accounting/       # Các trang JSP cho kế toán
│   ├── common/           # Chứa các thành phần dùng chung (Header, Footer)
│   ├── login.jsp         # Trang đăng nhập hệ thống
│   └── home.jsp          # Trang chủ sau khi đăng nhập (non-admin)