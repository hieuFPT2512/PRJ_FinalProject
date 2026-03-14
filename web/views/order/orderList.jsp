<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📋 Delivery Orders</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderForm" class="btn btn-primary btn-sm">+ Create New Order</a>
    </div>
    <div class="card-body">
        <table>
            <thead>
                <tr><th>#</th><th>Customer</th><th>Warehouse</th><th>Route</th><th>COD</th><th>Total Amount</th><th>Status</th><th>Created Date</th><th>Action</th></tr>
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
                            <c:when test="${o.hasCod == 'Y'}"><span class="badge badge-warning">Yes COD</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">No</span></c:otherwise>
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
                        <a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${o.orderId}" class="btn btn-secondary btn-sm">Detail</a>
                        <a href="<%= request.getContextPath() %>/main?action=orderForm&id=${o.orderId}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="<%= request.getContextPath() %>/main?action=orderDelete&id=${o.orderId}" class="btn btn-danger btn-sm" onclick="return confirm('Delete this order?')">Delete</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty orders}">
                    <tr><td colspan="9" style="text-align:center;color:#aaa;">No data available</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>