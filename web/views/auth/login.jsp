<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - DeliverAcct</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="login-wrapper">
    <div class="login-card">
        <h1>🚚 DeliverAcct</h1>
        <p class="subtitle">Delivery Management &amp; Auto Alert System</p>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-box error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post" action="<%= request.getContextPath() %>/main?action=login">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" class="form-control" placeholder="Enter username..." required autofocus>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control" placeholder="Enter password..." required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:10px;font-size:15px;margin-top:10px;">
                Login
            </button>
        </form>
    </div>
</div>
</body>
</html>