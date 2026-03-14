<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty order ? '➕ Create Delivery Order' : '✏️ Edit Delivery Order'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body" style="max-width:700px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=orderSave">
            <input type="hidden" name="orderId" value="${order.orderId}">
            <div class="form-row">
                <div class="form-group">
                    <label>Customer *</label>
                    <select name="customerId" class="form-control" required>
                        <option value="">-- Select customer --</option>
                        <c:forEach var="c" items="${customers}">
                            <option value="${c.customerId}" ${order.customerId == c.customerId ? 'selected' : ''}>${c.customerName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Warehouse *</label>
                    <select name="warehouseId" class="form-control" required>
                        <option value="">-- Select warehouse --</option>
                        <c:forEach var="w" items="${warehouses}">
                            <option value="${w.warehouseId}" ${order.warehouseId == w.warehouseId ? 'selected' : ''}>${w.warehouseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Delivery Route</label>
                    <input type="text" name="route" class="form-control" value="${order.route}" placeholder="Example: HN-1">
                </div>
                <div class="form-group">
                    <label>COD Collection</label>
                    <select name="hasCod" class="form-control">
                        <option value="N" ${order.hasCod == 'N' ? 'selected' : ''}>No</option>
                        <option value="Y" ${order.hasCod == 'Y' ? 'selected' : ''}>Yes COD</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Total Amount</label>
                    <input type="number" step="0.01" name="totalAmount" class="form-control" value="${order.totalAmount}">
                </div>
                <div class="form-group">
                    <label>Status</label>
                    <select name="status" class="form-control">
                        <option value="Pending"   ${order.status == 'Pending'   ? 'selected' : ''}>Pending</option>
                        <option value="Shipping"  ${order.status == 'Shipping'  ? 'selected' : ''}>Shipping</option>
                        <option value="Delivered" ${order.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
                        <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">💾 Save Order</button>
            <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>