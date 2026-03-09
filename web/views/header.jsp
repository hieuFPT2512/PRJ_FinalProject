<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    model.User loggedUser = (model.User) session.getAttribute("loggedUser");
    String roleName = (loggedUser != null) ? loggedUser.getRoleName() : "";
    String role     = roleName; // alias để tương thích với các JSP cũ dùng biến "role"
    int roleId      = (loggedUser != null) ? loggedUser.getRoleId()   : 0;

    // Hằng số role — khớp với utils.RoleConstants
    final int ADMIN            = 1;
    final int ACCOUNTANT       = 2;
    final int WAREHOUSE_STAFF  = 3;
    final int DRIVER           = 4;
    final int CUSTOMER_SERVICE = 5;

    // Nhóm quyền dùng để ẩn/hiện menu
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
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DeliverAcct Auto Alert</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <a class="brand" href="<%= request.getContextPath() %>/main?action=dashboard">🚚 DeliverAcct</a>
    <span class="user-info">
        Xin chào, <strong><%= loggedUser != null ? loggedUser.getFullName() : "" %></strong>
        (<%= roleName %>) &nbsp;|&nbsp;
        <a href="<%= request.getContextPath() %>/main?action=logout">Đăng xuất</a>
    </span>
</nav>

<div class="wrapper">
<aside class="sidebar">

    <%-- ── TỔNG QUAN: tất cả role ── --%>
    <div class="menu-title">Tổng quan</div>
    <a href="<%= request.getContextPath() %>/main?action=dashboard">🏠 Dashboard</a>

    <%-- ── DANH MỤC: ADMIN, Thủ kho, CSKH ── --%>
    <% if (canViewMasterData) { %>
    <div class="menu-title">Danh mục</div>
    <a href="<%= request.getContextPath() %>/main?action=customerList">👥 Khách hàng</a>
    <a href="<%= request.getContextPath() %>/main?action=productList">📦 Sản phẩm</a>
    <a href="<%= request.getContextPath() %>/main?action=warehouseList">🏭 Kho hàng</a>
    <% } %>

    <%-- ── GIAO HÀNG ── --%>
    <% if (canViewDelivery) { %>
    <div class="menu-title">Giao hàng</div>
    <a href="<%= request.getContextPath() %>/main?action=orderList">📋 Đơn giao hàng</a>
    <% } %>
    <%-- Lô hàng và POD: ADMIN, Thủ kho, Tài xế --%>
    <% if (canViewShipmentPod) { %>
    <a href="<%= request.getContextPath() %>/main?action=shipmentList">🚛 Lô hàng</a>
    <a href="<%= request.getContextPath() %>/main?action=podList">📸 Bằng chứng giao</a>
    <% } %>

    <%-- ── KHO: ADMIN, Thủ kho ── --%>
    <% if (canViewWarehouse) { %>
    <div class="menu-title">Kho hàng</div>
    <a href="<%= request.getContextPath() %>/main?action=outboundList">⬆️ Xuất kho</a>
    <a href="<%= request.getContextPath() %>/main?action=inboundList">⬇️ Nhập kho</a>
    <a href="<%= request.getContextPath() %>/main?action=stockLedger">📊 Sổ cái tồn kho</a>
    <% } %>

    <%-- ── KẾ TOÁN: ADMIN, Kế toán ── --%>
    <% if (canViewAccounting) { %>
    <div class="menu-title">Kế toán</div>
    <a href="<%= request.getContextPath() %>/main?action=invoiceList">🧾 Hóa đơn</a>
    <a href="<%= request.getContextPath() %>/main?action=paymentList">💳 Thanh toán</a>
    <a href="<%= request.getContextPath() %>/main?action=codList">💵 Đối soát COD</a>
    <% } %>

    <%-- ── TÌM KIẾM: hiện từng mục theo quyền ── --%>
    <% if (canSearchOrders || canSearchStock || canSearchInvoices) { %>
    <div class="menu-title">Tìm kiếm</div>
    <% if (canSearchOrders) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchOrders">🔍 Tìm đơn giao</a>
    <% } %>
    <% if (canSearchStock) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchStockDocs">🔍 Tìm chứng từ kho</a>
    <% } %>
    <% if (canSearchInvoices) { %>
    <a href="<%= request.getContextPath() %>/main?action=searchInvoices">🔍 Tìm hóa đơn</a>
    <% } %>
    <% } %>

    <%-- ── CẢNH BÁO & CASE: tất cả role ── --%>
    <div class="menu-title">Cảnh báo</div>
    <a href="<%= request.getContextPath() %>/main?action=alertList">🔔 Danh sách cảnh báo</a>
    <a href="<%= request.getContextPath() %>/main?action=caseList">📁 Case xử lý</a>

    <%-- ── QUẢN TRỊ: chỉ ADMIN ── --%>
    <% if (isAdmin) { %>
    <div class="menu-title">Quản trị</div>
    <a href="<%= request.getContextPath() %>/main?action=userList">👤 Người dùng</a>
    <a href="<%= request.getContextPath() %>/main?action=auditLog">📝 Audit Log</a>
    <% } %>

</aside>
<div class="main-content">
