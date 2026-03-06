<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📋 Chi tiết Đơn #${order.orderId}</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body">
        <table style="width:auto;margin-bottom:20px;">
            <tr><td style="padding:6px 15px;font-weight:600;">Khách hàng:</td><td>${order.customerName}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Kho xuất:</td><td>${order.warehouseName}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Tuyến:</td><td>${order.route}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">COD:</td><td>${order.hasCod == 'Y' ? 'Có' : 'Không'}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Tổng tiền:</td><td>${order.totalAmount}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Trạng thái:</td><td>${order.status}</td></tr>
            <tr><td style="padding:6px 15px;font-weight:600;">Ngày tạo:</td><td>${order.orderDate}</td></tr>
        </table>

        <h3 style="color:#1e3a5f;margin-bottom:10px;">Danh sách hàng trong đơn</h3>
        <table>
            <thead><tr><th>#</th><th>SKU</th><th>Sản phẩm</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead>
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
