<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - DeliverAcct</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="login-wrapper">
    <div class="login-card">
        <h1>🚚 DeliverAcct</h1>
        <p class="subtitle">Hệ thống quản lý Delivery &amp; Auto Alert</p>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-box error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post" action="<%= request.getContextPath() %>/main?action=login">
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" placeholder="Nhập username..." required autofocus>
            </div>
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu..." required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:10px;font-size:15px;margin-top:10px;">
                Đăng nhập
            </button>
        </form>
    </div>
</div>
</body>
</html>
