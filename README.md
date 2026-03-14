# 🚚 DeliverAcct System (DeliverAcctAutoAlert)

A comprehensive **Logistics Operations and Warehouse Accounting Management System**, built using the **MVC (Model–View–Controller) architecture** with **Java Servlets and JSP**.

This project manages the entire operational workflow—from **order creation, delivery dispatching, warehouse inventory management**, to **Cash on Delivery (COD) reconciliation and automated risk alerting**.

---

# 🌟 Key Features (Modules)

The system implements **strict role-based access control**, providing **separate dashboards for different user roles**.

## 🛡️ Admin Module (System Administration)
- Manage **Users and Roles**.
- Record **system activity logs (Audit Logs)**.
- Configure **risk alert rules (Alert Rules)**.

## 📦 Master Data Module
- Manage core data including **Customers, Products, and Warehouses**.

## 🏍️ Delivery Module (Dispatch & Delivery Management)
- Create and manage **Orders**.
- Assign and manage **Shipments and drivers**.
- Update delivery status and upload **POD (Proof of Delivery)**.

## 🏭 Warehouse Module (Warehouse Accounting)
- Create **Inbound and Outbound warehouse transactions**.
- Monitor the **real-time Stock Ledger**.

## 💰 Accounting Module (Finance & Reconciliation)
- Manage **Invoices and Payment History**.
- Perform **COD reconciliation** using the **Reconciliation Workbench** and investigate discrepancies.

## 🚨 Risk Control Module
- Monitor **system alerts**.
- Create and manage **exception handling cases**.

---

# 🛠️ Technology Stack

- **Programming Language:** Java (JDK 8 update 172)
- **Web Technologies:** Java Servlets, JSP (JavaServer Pages), JSTL
- **Architecture:** MVC (Model – View – Controller), Front Controller Pattern (`MainController`)
- **Database:** SQL Server / MySQL (connected via JDBC)
- **Frontend:** Pure HTML (optimized for performance and focused on business logic workflows)
- **IDE:** Apache NetBeans / Eclipse
- **Web Server:** Apache Tomcat 9.0.113.

---

# 📂 Project Structure (Architecture)

The project strictly follows the **MVC architecture**, separating **business logic, presentation layer, and database interaction**.

```text
DeliverAcctAutoAlert/
├── src/java/
│   ├── authentication/   # Authentication-related models (User, Role...)
│   ├── controller/       # MainController (Router) and business Controllers
│   ├── dao/              # Data Access Objects (SQL queries and database interaction)
│   ├── model/            # Entity classes (Customer, Product, Order...)
│   └── utils/            # Utility classes (e.g., DBUtils for database connection)
│
├── WebContent/ (or Web Pages/)
│   ├── WEB-INF/          # web.xml configuration
│   ├── admin/            # Admin JSP pages (Dashboard, UserList, UserForm...)
│   ├── delivery/         # Delivery operation pages
│   ├── warehouse/        # Warehouse management pages
│   ├── accounting/       # Accounting module pages
│   ├── common/           # Shared components (Header, Footer)
│   ├── login.jsp         # System login page
│   └── home.jsp          # Home page after login (non-admin users)
