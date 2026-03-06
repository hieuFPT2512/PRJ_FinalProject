<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📋 Đơn giao hàng</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderForm" class="btn btn-primary btn-sm">+ Tạo đơn mới</a>
    </div>
    <div class="card-body">
        <table>
            <thead>
                <tr><th>#</th><th>Khách hàng</th><th>Kho xuất</th><th>Tuyến</th><th>COD</th><th>Tổng tiền</th><th>Trạng thái</th><th>Ngày tạo</th><th>Thao tác</th></tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orders}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.customerName}</td>
                    <td>${o.warehouseName}</td>
                    <td>${o.route}</td>
                    <td>
                        <c:choose>
                            <c:when test="${o.hasCod == 'Y'}"><span class="badge badge-warning">Có COD</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">Không</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align:right;">${o.totalAmount}</td>
                    <td>
                        <c:choose>
                            <c:when test="${o.status == 'Delivered'}"><span class="badge badge-success">${o.status}</span></c:when>
                            <c:when test="${o.status == 'Shipping'}"><span class="badge badge-info">${o.status}</span></c:when>
                            <c:when test="${o.status == 'Cancelled'}"><span class="badge badge-danger">${o.status}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${o.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${o.orderDate}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${o.orderId}" class="btn btn-secondary btn-sm">Chi tiết</a>
                        <a href="<%= request.getContextPath() %>/main?action=orderForm&id=${o.orderId}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="<%= request.getContextPath() %>/main?action=orderDelete&id=${o.orderId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa đơn này?')">Xóa</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty orders}">
                    <tr><td colspan="9" style="text-align:center;color:#aaa;">Không có dữ liệu</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
