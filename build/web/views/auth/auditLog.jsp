<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>📝 Audit Log</h2></div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Người dùng</th><th>Hành động</th><th>Bảng</th><th>Record ID</th><th>Thời điểm</th></tr></thead>
            <tbody>
                <c:forEach var="l" items="${logs}">
                <tr>
                    <td>${l.logId}</td>
                    <td>${l.username}</td>
                    <td>
                        <c:choose>
                            <c:when test="${l.action=='DELETE' || l.action=='CLOSE'}"><span class="badge badge-danger">${l.action}</span></c:when>
                            <c:when test="${l.action=='INSERT'}"><span class="badge badge-success">${l.action}</span></c:when>
                            <c:when test="${l.action=='UPDATE'}"><span class="badge badge-warning">${l.action}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${l.action}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${l.tableName}</td>
                    <td>${l.recordId}</td>
                    <td>${l.actionTime}</td>
                </tr>
                </c:forEach>
                <c:if test="${empty logs}">
                    <tr><td colspan="6" style="text-align:center;color:#aaa;">Chưa có log nào</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
