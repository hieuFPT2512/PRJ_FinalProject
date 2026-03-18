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
- **Web Server:** Apache Tomcat 9.0.113

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
```

---

# ⚙️ Prerequisites & Installation

## Prerequisites

Before running the project, make sure you have the following installed:

| Tool | Version | Notes |
|------|---------|-------|
| JDK | 8 (update 172+) | [Download](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) |
| Apache Tomcat | 9.0.x | [Download](https://tomcat.apache.org/download-90.cgi) |
| SQL Server / MySQL | Latest stable | Either database is supported |
| Apache NetBeans / Eclipse | Latest stable | Recommended IDE |

---

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/your-username/DeliverAcctAutoAlert.git
```

### 2. Import the project into your IDE

- **NetBeans:** `File` → `Open Project` → select the cloned folder
- **Eclipse:** `File` → `Import` → `Existing Projects into Workspace`

### 3. Set up the database

- Create a new database named `deliver_acct_db` (or your preferred name)
- Run the provided SQL script to initialize the schema and seed data:

```bash
# SQL Server
sqlcmd -S localhost -U sa -P yourpassword -d deliver_acct_db -i database/schema.sql

# MySQL
mysql -u root -p deliver_acct_db < database/schema.sql
```

### 4. Configure the database connection

Open `src/java/utils/DBUtils.java` and update the connection details:

```java
private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=deliver_acct_db";
// or for MySQL:
// private static final String URL = "jdbc:mysql://localhost:3306/deliver_acct_db";

private static final String USERNAME = "your_db_username";
private static final String PASSWORD = "your_db_password";
```

### 5. Deploy to Tomcat and access the application

- **NetBeans:** Right-click project → `Run` (auto-deploys to configured Tomcat)
- **Eclipse:** Right-click project → `Run As` → `Run on Server` → select Tomcat 9
- Access the application


# 👥 Contributors

| Name | Role |
|------|------|
| Mai Xuan Hieu | UI/UX Design |
| Dao Minh Khoi | Database Design |
| Dao Hoang Nhat | Logic & Testing |
| Hoang Thach Thai Son | Logic & Testing |