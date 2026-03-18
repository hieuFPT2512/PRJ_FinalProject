<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🏠 Dashboard</h2></div>
    <div class="card-body">
        <p style="color:#555;margin-bottom:20px;">Hello, <strong><%= loggedUser != null ? loggedUser.getFullName() : "" %></strong>! Role: <strong><%= role %></strong></p>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number" style="color:#1e6fbe;">Delivery Orders</div>
                <div class="stat-label">Manage delivery orders</div>
                <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-primary btn-sm" style="margin-top:10px;">View →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#2e7d32;">Warehouse</div>
                <div class="stat-label">Inbound, outbound &amp; inventory</div>
                <a href="<%= request.getContextPath() %>/main?action=stockLedger" class="btn btn-success btn-sm" style="margin-top:10px;">View →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#f57f17;">Alerts</div>
                <div class="stat-label">Alert &amp; case management</div>
                <a href="<%= request.getContextPath() %>/main?action=alertList" class="btn btn-warning btn-sm" style="margin-top:10px;">View →</a>
            </div>
            <div class="stat-card">
                <div class="stat-number" style="color:#c62828;">COD</div>
                <div class="stat-label">Cash-on-delivery reconciliation</div>
                <a href="<%= request.getContextPath() %>/main?action=codList" class="btn btn-danger btn-sm" style="margin-top:10px;">View →</a>
            </div>
        </div>

        <% if ("Admin".equals(role)) { %>
        <div class="alert-box warning">⚙️ You are logged in with <strong>Admin</strong> privileges. Full system access granted.</div>
        <% } else if ("Accountant".equals(role)) { %>
        <div class="alert-box success">💼 Accountant role: Manage invoices, payments, and COD reconciliation.</div>
        <% } else if ("Warehouse Staff".equals(role)) { %>
        <div class="alert-box success">🏭 Warehouse role: Manage inbound/outbound operations and inventory ledger.</div>
        <% } else if ("Driver".equals(role)) { %>
        <div class="alert-box success">🚛 Driver role: View and update assigned shipments.</div>
        <% } %>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>