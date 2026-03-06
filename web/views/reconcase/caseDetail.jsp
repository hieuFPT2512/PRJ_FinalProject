<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📁 Chi tiết Case #${recase.caseId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=caseList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body">
        <table style="width:auto;margin-bottom:20px;">
            <tr><td style="padding:8px 15px;font-weight:600;">Đơn hàng:</td><td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${recase.orderId}">#${recase.orderId}</a></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Khách hàng:</td><td>${recase.customerName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Loại sai lệch:</td><td><strong>${recase.issueType}</strong></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Quy tắc alert:</td><td>${recase.ruleName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Phụ trách:</td><td>${recase.assignedName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Trạng thái:</td><td>${recase.status}</td></tr>
        </table>

        <c:if test="${recase.status != 'Closed'}">
        <div style="padding:15px;background:#f8f9fa;border-radius:8px;">
            <h4 style="margin-bottom:10px;color:#1e3a5f;">Giao case cho nhân viên khác</h4>
            <form method="post" action="<%= request.getContextPath() %>/main?action=caseAssign" style="display:flex;gap:10px;">
                <input type="hidden" name="caseId" value="${recase.caseId}">
                <input type="number" name="userId" class="form-control" style="width:120px;" placeholder="User ID">
                <button type="submit" class="btn btn-warning">🔄 Giao case</button>
            </form>
        </div>
        </c:if>

        <div style="margin-top:15px;">
            <c:if test="${recase.status != 'Closed'}">
                <a href="<%= request.getContextPath() %>/main?action=caseClose&id=${recase.caseId}" class="btn btn-success"
                   onclick="return confirm('Đóng case này?')">✔ Đóng case</a>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
