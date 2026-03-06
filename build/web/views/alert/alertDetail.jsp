<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🔔 Chi tiết Cảnh báo #${alert.alertId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=alertList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body">
        <table style="width:auto;">
            <tr><td style="padding:8px 15px;font-weight:600;">Đơn hàng:</td><td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${alert.orderId}">#${alert.orderId}</a></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Khách hàng:</td><td>${alert.customerName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Quy tắc:</td><td>${alert.ruleName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Mức độ:</td><td><span class="badge severity-${alert.severity}">${alert.severity}</span></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Risk Score:</td><td><strong style="font-size:18px;color:#c62828;">${alert.riskScore}</strong></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Trạng thái:</td><td>${alert.status}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Phát hiện lúc:</td><td>${alert.detectedAt}</td></tr>
        </table>
        <div style="margin-top:20px;">
            <a href="<%= request.getContextPath() %>/main?action=alertClose&id=${alert.alertId}" class="btn btn-success"
               onclick="return confirm('Đóng cảnh báo này?')">✔ Đóng cảnh báo</a>
            <a href="<%= request.getContextPath() %>/main?action=caseList" class="btn btn-primary">📁 Xem Cases liên quan</a>
        </div>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
