<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📋 Order Details #${order.orderId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body">
        <table style="width:auto;margin-bottom:20px;">
            <tr><td style="padding:6px 15px;font-weight:600;">Customer:</td><td>${order.customerName}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Warehouse:</td><td>${order.warehouseName}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Route:</td><td>${order.route}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">COD:</td><td>${order.hasCod == 'Y' ? 'Yes' : 'No'}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Total Amount:</td><td>${order.totalAmount}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Status:</td><td>${order.status}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Created Date:</td><td>${order.orderDate}</td></tr>
        </table>

        <h3 style="color:#1e3a5f;margin-bottom:10px;">Order Item List</h3>
        <table>
            <thead><tr><th>#</th><th>SKU</th><th>Product</th><th>Qty</th><th>Unit Price</th><th>Subtotal</th></tr></thead>
            <tbody>
                <c:forEach var="item" items="${order.items}">
                <tr>
                    <td>${item.orderItemId}</td>
                    <td>${item.sku}</td>
                    <td>${item.productName}</td>
                    <td>${item.qty}</td>
                    <td style="text-align:right;">${item.unitPrice}</td>
                    <td style="text-align:right;font-weight:600;">${item.subtotal}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>