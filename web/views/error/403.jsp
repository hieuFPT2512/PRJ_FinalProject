<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/header.jsp" %>
<style>
    .error-container {
        display: flex; flex-direction: column; align-items: center;
        justify-content: center; min-height: 60vh; text-align: center;
    }
    .error-code { font-size: 80px; font-weight: bold; color: #e74c3c; margin: 0; }
    .error-msg  { font-size: 20px; color: #555; margin: 10px 0 30px; }
    .btn-back   { background: #2c3e50; color: #fff; padding: 10px 25px;
                  border-radius: 6px; text-decoration: none; font-size: 15px; }
    .btn-back:hover { background: #34495e; }
</style>

<div class="error-container">
    <p class="error-code">403</p>
    <p class="error-msg">
        <%= request.getAttribute("errorMessage") != null
            ? request.getAttribute("errorMessage")
            : "Bạn không có quyền truy cập chức năng này." %>
    </p>
    <a href="<%= request.getContextPath() %>/main?action=dashboard" class="btn-back">
        ← Về Dashboard
    </a>
</div>

<%@ include file="/views/footer.jsp" %>
