<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🧾 Hóa đơn #${invoice.invoiceId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=invoiceList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body">
        <table style="width:auto;">
            <tr><td style="padding:8px 15px;font-weight:600;">Đơn hàng:</td><td>#${invoice.orderId}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Khách hàng:</td><td>${invoice.customerName}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Ngày xuất:</td><td>${invoice.issueDate}</td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Tổng tiền:</td><td><strong>${invoice.totalAmount}</strong></td></tr>
            <tr><td style="padding:8px 15px;font-weight:600;">Trạng thái:</td><td>${invoice.status}</td></tr>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
