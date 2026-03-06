<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    model.User loggedUser = (model.User) session.getAttribute("loggedUser");
    String role = (loggedUser != null) ? loggedUser.getRoleName() : "";
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
        (<%= role %>) &nbsp;|&nbsp;
        <a href="<%= request.getContextPath() %>/main?action=logout">Đăng xuất</a>
    </span>
</nav>

<div class="wrapper">
<aside class="sidebar">
    <div class="menu-title">Tổng quan</div>
    <a href="<%= request.getContextPath() %>/main?action=dashboard">🏠 Dashboard</a>

    <div class="menu-title">Danh mục</div>
    <a href="<%= request.getContextPath() %>/main?action=customerList">👥 Khách hàng</a>
    <a href="<%= request.getContextPath() %>/main?action=productList">📦 Sản phẩm</a>
    <a href="<%= request.getContextPath() %>/main?action=warehouseList">🏭 Kho hàng</a>

    <div class="menu-title">Giao hàng</div>
    <a href="<%= request.getContextPath() %>/main?action=orderList">📋 Đơn giao hàng</a>
    <a href="<%= request.getContextPath() %>/main?action=shipmentList">🚛 Lô hàng</a>
    <a href="<%= request.getContextPath() %>/main?action=podList">📸 Bằng chứng giao</a>

    <div class="menu-title">Kho &amp; Kế toán</div>
    <a href="<%= request.getContextPath() %>/main?action=outboundList">⬆️ Xuất kho</a>
    <a href="<%= request.getContextPath() %>/main?action=inboundList">⬇️ Nhập kho</a>
    <a href="<%= request.getContextPath() %>/main?action=stockLedger">📊 Sổ cái tồn kho</a>
    <a href="<%= request.getContextPath() %>/main?action=invoiceList">🧾 Hóa đơn</a>
    <a href="<%= request.getContextPath() %>/main?action=paymentList">💳 Thanh toán</a>
    <a href="<%= request.getContextPath() %>/main?action=codList">💵 Đối soát COD</a>

    <div class="menu-title">Tìm kiếm</div>
    <a href="<%= request.getContextPath() %>/main?action=searchOrders">🔍 Tìm đơn giao</a>
    <a href="<%= request.getContextPath() %>/main?action=searchStockDocs">🔍 Tìm chứng từ kho</a>
    <a href="<%= request.getContextPath() %>/main?action=searchInvoices">🔍 Tìm hóa đơn</a>

    <div class="menu-title">Cảnh báo</div>
    <a href="<%= request.getContextPath() %>/main?action=alertList">🔔 Danh sách cảnh báo</a>
    <a href="<%= request.getContextPath() %>/main?action=caseList">📁 Case xử lý</a>

    <% if ("Admin".equals(role)) { %>
    <div class="menu-title">Quản trị</div>
    <a href="<%= request.getContextPath() %>/main?action=userList">👤 Người dùng</a>
    <a href="<%= request.getContextPath() %>/main?action=auditLog">📝 Audit Log</a>
    <% } %>
</aside>
<div class="main-content">
