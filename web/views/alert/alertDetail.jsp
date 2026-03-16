<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🔔 Alert Details #${alert.alertId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=alertList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body">
        <table style="width:auto;">
            <tr><td style="padding:8px 15px;font-weight:600;">Order:</td><td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${alert.orderId}">#${alert.orderId}</a></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Customer:</td><td>${alert.customerName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Rule:</td><td>${alert.ruleName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Severity:</td><td><span class="badge severity-${alert.severity}">${alert.severity}</span></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Status:</td><td>${alert.status}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Detected At:</td><td>${alert.detectedAt}</td></tr>
        </table>
        <div style="margin-top:20px;">
            <a href="<%= request.getContextPath() %>/main?action=alertClose&id=${alert.alertId}" class="btn btn-success"
               onclick="return confirm('Close this alert?')">✔ Close Alert</a>
            <a href="<%= request.getContextPath() %>/main?action=caseList" class="btn btn-primary">📁 View Related Cases</a>
        </div>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>