<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🏠 Dashboard</h2></div>
    <div class="card-body">
        <p style="color:#555;margin-bottom:20px;">Xin chào, <strong><%= loggedUser != null ? loggedUser.getFullName() : "" %></strong>! Vai trò: <strong><%= role %></strong></p>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number" style="color:#1e6fbe;">Đơn Giao</div>
                <div class="stat-label">Quản lý delivery orders</div>
                <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-primary btn-sm" style="margin-top:10px;">Xem →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#2e7d32;">Kho hàng</div>
                <div class="stat-label">Xuất nhập &amp; tồn kho</div>
                <a href="<%= request.getContextPath() %>/main?action=stockLedger" class="btn btn-success btn-sm" style="margin-top:10px;">Xem →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#f57f17;">Cảnh báo</div>
                <div class="stat-label">Alert &amp; Case xử lý</div>
                <a href="<%= request.getContextPath() %>/main?action=alertList" class="btn btn-warning btn-sm" style="margin-top:10px;">Xem →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#c62828;">COD</div>
                <div class="stat-label">Đối soát thu hộ</div>
                <a href="<%= request.getContextPath() %>/main?action=codList" class="btn btn-danger btn-sm" style="margin-top:10px;">Xem →</a>
            </div>
        </div>

        <% if ("Admin".equals(role)) { %>
        <div class="alert-box warning">⚙️ Bạn đang đăng nhập với quyền <strong>Admin</strong>. Có toàn quyền truy cập hệ thống.</div>
        <% } else if ("Accountant".equals(role)) { %>
        <div class="alert-box success">💼 Vai trò Kế toán: Quản lý hóa đơn, thanh toán, và đối soát COD.</div>
        <% } else if ("Warehouse Staff".equals(role)) { %>
        <div class="alert-box success">🏭 Vai trò Kho: Quản lý xuất nhập kho và sổ cái tồn kho.</div>
        <% } else if ("Driver".equals(role)) { %>
        <div class="alert-box success">🚛 Vai trò Tài xế: Xem và cập nhật lô hàng đang giao.</div>
        <% } %>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
