<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🚛 Shipments</h2>
        <a href="<%= request.getContextPath() %>/main?action=shipmentForm" class="btn btn-primary btn-sm">+ Create New Shipment</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Order #</th><th>Customer</th><th>Driver</th><th>Route</th><th>Ship Date</th><th>Delivery Date</th><th>Status</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="s" items="${shipments}">
                <tr>
                    <td>${s.shipmentId}</td>
                    <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${s.orderId}">#${s.orderId}</a></td>
                    <td>${s.customerName}</td>
                    <td>${s.driverName}</td>
                    <td>${s.route}</td>
                    <td>${s.shipDate}</td>
                    <td>${s.deliveryDate}</td>
                    <td><span class="badge badge-info">${s.status}</span></td>
                    <td>
                        <form method="post" action="<%= request.getContextPath() %>/main?action=shipmentUpdate" style="display:inline-flex;gap:5px;">
                            <input type="hidden" name="shipmentId" value="${s.shipmentId}">
                            <input type="hidden" name="driverName" value="${s.driverName}">
                            <select name="status" class="form-control" style="width:120px;padding:4px;">
                                <option value="Shipping"  ${s.status=='Shipping'  ? 'selected':''}>Shipping</option>
                                <option value="Delivered" ${s.status=='Delivered' ? 'selected':''}>Delivered</option>
                                <option value="Cancelled" ${s.status=='Cancelled' ? 'selected':''}>Cancelled</option>
                            </select>
                            <button type="submit" class="btn btn-success btn-sm">Update</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>