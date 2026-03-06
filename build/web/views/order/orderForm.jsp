<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty order ? '➕ Tạo Đơn giao' : '✏️ Sửa Đơn giao'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body" style="max-width:700px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=orderSave">
            <input type="hidden" name="orderId" value="${order.orderId}">
            <div class="form-row">
                <div class="form-group">
                    <label>Khách hàng *</label>
                    <select name="customerId" class="form-control" required>
                        <option value="">-- Chọn khách hàng --</option>
                        <c:forEach var="c" items="${customers}">
                            <option value="${c.customerId}" ${order.customerId == c.customerId ? 'selected' : ''}>${c.customerName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Kho xuất *</label>
                    <select name="warehouseId" class="form-control" required>
                        <option value="">-- Chọn kho --</option>
                        <c:forEach var="w" items="${warehouses}">
                            <option value="${w.warehouseId}" ${order.warehouseId == w.warehouseId ? 'selected' : ''}>${w.warehouseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Tuyến giao</label>
                    <input type="text" name="route" class="form-control" value="${order.route}" placeholder="VD: HN-1">
                </div>
                <div class="form-group">
                    <label>Thu hộ COD</label>
                    <select name="hasCod" class="form-control">
                        <option value="N" ${order.hasCod == 'N' ? 'selected' : ''}>Không</option>
                        <option value="Y" ${order.hasCod == 'Y' ? 'selected' : ''}>Có COD</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Tổng tiền</label>
                    <input type="number" step="0.01" name="totalAmount" class="form-control" value="${order.totalAmount}">
                </div>
                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status" class="form-control">
                        <option value="Pending"   ${order.status == 'Pending'   ? 'selected' : ''}>Pending</option>
                        <option value="Shipping"  ${order.status == 'Shipping'  ? 'selected' : ''}>Shipping</option>
                        <option value="Delivered" ${order.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
                        <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">💾 Lưu đơn</button>
            <a href="<%= request.getContextPath() %>/main?action=orderList" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
