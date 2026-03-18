<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🔔 Alert List (Open)</h2></div>
    <div class="card-body">
        <c:if test="${empty alerts}">
            <div class="alert-box success">✅ No open alerts.</div>
        </c:if>
        <table>
            <thead><tr><th>#</th><th>Order #</th><th>Customer</th><th>Rule</th><th>Severity</th><th>Time</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="a" items="${alerts}">
                <tr>
                    <td>${a.alertId}</td>
                    <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${a.orderId}">#${a.orderId}</a></td>
                    <td>${a.customerName}</td>
                    <td>${a.ruleName}</td>
                    <td><span class="badge severity-${a.severity}">${a.severity}</span></td>
                    <td>${a.detectedAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=alertDetail&id=${a.alertId}" class="btn btn-secondary btn-sm">Detail</a>
                        <a href="<%= request.getContextPath() %>/main?action=alertClose&id=${a.alertId}" class="btn btn-success btn-sm"
                           onclick="return confirm('Close this alert?')">✔ Close</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>