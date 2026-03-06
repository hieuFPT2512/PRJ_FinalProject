<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🔔 Danh sách Cảnh báo (Open)</h2></div>
    <div class="card-body">
        <c:if test="${empty alerts}">
            <div class="alert-box success">✅ Không có cảnh báo nào đang mở.</div>
        </c:if>
        <table>
            <thead><tr><th>#</th><th>Đơn #</th><th>Khách hàng</th><th>Quy tắc</th><th>Mức độ</th><th>Risk Score</th><th>Thời điểm</th><th>Thao tác</th></tr></thead>
            <tbody>
                <c:forEach var="a" items="${alerts}">
                <tr>
                    <td>${a.alertId}</td>
                    <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${a.orderId}">#${a.orderId}</a></td>
                    <td>${a.customerName}</td>
                    <td>${a.ruleName}</td>
                    <td><span class="badge severity-${a.severity}">${a.severity}</span></td>
                    <td style="font-weight:600;color:${a.riskScore >= 8 ? '#c62828' : '#f57f17'};">${a.riskScore}</td>
                    <td>${a.detectedAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=alertDetail&id=${a.alertId}" class="btn btn-secondary btn-sm">Chi tiết</a>
                        <a href="<%= request.getContextPath() %>/main?action=alertClose&id=${a.alertId}" class="btn btn-success btn-sm"
                           onclick="return confirm('Đóng cảnh báo này?')">✔ Đóng</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
