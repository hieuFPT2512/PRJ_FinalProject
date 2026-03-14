<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>📁 Case Management List</h2></div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Order #</th><th>Customer</th><th>Issue Type</th><th>Alert #</th><th>Rule</th><th>Assigned To</th><th>Status</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="c" items="${cases}">
                <tr>
                    <td>${c.caseId}</td>
                    <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${c.orderId}">#${c.orderId}</a></td>
                    <td>${c.customerName}</td>
                    <td>${c.issueType}</td>
                    <td>${c.alertId}</td>
                    <td>${c.ruleName}</td>
                    <td>${c.assignedName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${c.status=='Closed'}"><span class="badge badge-success">${c.status}</span></c:when>
                            <c:when test="${c.status=='Open'}"><span class="badge badge-danger">${c.status}</span></c:when>
                            <c:when test="${c.status=='Investigating'}"><span class="badge badge-warning">${c.status}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${c.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=caseDetail&id=${c.caseId}" class="btn btn-secondary btn-sm">Detail</a>
                        <c:if test="${c.status != 'Closed'}">
                            <a href="<%= request.getContextPath() %>/main?action=caseClose&id=${c.caseId}" class="btn btn-success btn-sm"
                               onclick="return confirm('Close this case?')">✔ Close</a>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty cases}">
                    <tr><td colspan="9" style="text-align:center;color:#aaa;">No cases available</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>