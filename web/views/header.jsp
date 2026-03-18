<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    model.User loggedUser = (model.User) session.getAttribute("loggedUser");
    String roleName = (loggedUser != null) ? loggedUser.getRoleName() : "";
    String role     = roleName; // alias to maintain compatibility with older JSPs using variable "role"
    int roleId      = (loggedUser != null) ? loggedUser.getRoleId()   : 0;

    // Role constants — match utils.RoleConstants
    final int ADMIN            = 1;
    final int ACCOUNTANT       = 2;
    final int WAREHOUSE_STAFF  = 3;
    final int DRIVER           = 4;
    final int CUSTOMER_SERVICE = 5;

    // Permission groups used to show/hide menu
    boolean canViewMasterData  = (roleId == ADMIN || roleId == WAREHOUSE_STAFF || roleId == CUSTOMER_SERVICE);
    boolean canViewDelivery    = (roleId == ADMIN || roleId == WAREHOUSE_STAFF || roleId == DRIVER || roleId == CUSTOMER_SERVICE);
    boolean canViewShipmentPod = (roleId == ADMIN || roleId == WAREHOUSE_STAFF || roleId == DRIVER);
    boolean canViewWarehouse   = (roleId == ADMIN || roleId == WAREHOUSE_STAFF);
    boolean canViewAccounting  = (roleId == ADMIN || roleId == ACCOUNTANT);
    boolean canSearchOrders    = (roleId == ADMIN || roleId == WAREHOUSE_STAFF || roleId == DRIVER || roleId == CUSTOMER_SERVICE);
    boolean canSearchStock     = (roleId == ADMIN || roleId == WAREHOUSE_STAFF);
    boolean canSearchInvoices  = (roleId == ADMIN || roleId == ACCOUNTANT);
    boolean isAdmin            = (roleId == ADMIN);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery Auto Alert</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="brand" href="<%= request.getContextPath() %>/main?action=dashboard">🚚 Delivery Auto Alert</a>
    <span class="user-info">
        Hello, <strong><%= loggedUser != null ? loggedUser.getFullName() : "" %></strong>
        (<%= roleName %>) &nbsp;|&nbsp;
        <a href="<%= request.getContextPath() %>/main?action=logout">Logout</a>
    </span>
</nav>

<div class="wrapper">
<aside class="sidebar">

    <%-- ── OVERVIEW: all roles ── --%>
    <div class="menu-title">Overview</div>
    <a href="<%= request.getContextPath() %>/main?action=dashboard">🏠 Dashboard</a>

    <%-- ── MASTER DATA: ADMIN, Warehouse staff, Customer service ── --%>
    <% if (canViewMasterData) { %>
    <div class="menu-title">Master Data</div>
    <a href="<%= request.getContextPath() %>/main?action=customerList">👥 Customers</a>
    <a href="<%= request.getContextPath() %>/main?action=productList">📦 Products</a>
    <a href="<%= request.getContextPath() %>/main?action=warehouseList">🏭 Warehouses</a>
    <% } %>

    <%-- ── DELIVERY ── --%>
    <% if (canViewDelivery) { %>
    <div class="menu-title">Delivery</div>
    <a href="<%= request.getContextPath() %>/main?action=orderList">📋 Delivery Orders</a>
    <% } %>
    <%-- Shipments and POD: ADMIN, Warehouse staff, Driver --%>
    <% if (canViewShipmentPod) { %>
    <a href="<%= request.getContextPath() %>/main?action=shipmentList">🚛 Shipments</a>
    <a href="<%= request.getContextPath() %>/main?action=podList">📸 Proof of Delivery</a>
    <% } %>

    <%-- ── WAREHOUSE: ADMIN, Warehouse staff ── --%>
    <% if (canViewWarehouse) { %>
    <div class="menu-title">Warehouse</div>
    <a href="<%= request.getContextPath() %>/main?action=outboundList">⬆️ Outbound</a>
    <a href="<%= request.getContextPath() %>/main?action=inboundList">⬇️ Inbound</a>
    <a href="<%= request.getContextPath() %>/main?action=stockLedger">📊 Stock Ledger</a>
    <% } %>

    <%-- ── ACCOUNTING: ADMIN, Accountant ── --%>
    <% if (canViewAccounting) { %>
    <div class="menu-title">Accounting</div>
    <a href="<%= request.getContextPath() %>/main?action=invoiceList">🧾 Invoices</a>
    <a href="<%= request.getContextPath() %>/main?action=paymentList">💳 Payments</a>
    <a href="<%= request.getContextPath() %>/main?action=codList">💵 COD Reconciliation</a>
    <% } %>

    <%-- ── SEARCH: show each item based on permission ── --%>
    <% if (canSearchOrders || canSearchStock || canSearchInvoices) { %>
    <div class="menu-title">Search</div>
    <% if (canSearchOrders) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchOrders">🔍 Search Orders</a>
    <% } %>
    <% if (canSearchStock) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchStockDocs">🔍 Search Warehouse Documents</a>
    <% } %>
    <% if (canSearchInvoices) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchInvoices">🔍 Search Invoices</a>
    <% } %>
    <% } %>

    <%-- ── ALERTS & CASES: all roles ── --%>
    <div class="menu-title">Alerts</div>
    <a href="<%= request.getContextPath() %>/main?action=alertList">🔔 Alert List</a>
    <a href="<%= request.getContextPath() %>/main?action=caseList">📁 Case Management</a>

    <%-- ── ADMINISTRATION: ADMIN only ── --%>
    <% if (isAdmin) { %>
    <div class="menu-title">Administration</div>
    <a href="<%= request.getContextPath() %>/main?action=userList">👤 Users</a>
    <a href="<%= request.getContextPath() %>/main?action=auditLog">📝 Audit Log</a>
    <% } %>

</aside>
<div class="main-content">