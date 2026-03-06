<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>➕ Tạo Phiếu Nhập kho</h2>
        <a href="<%= request.getContextPath() %>/main?action=inboundList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=inboundSave">
            <div class="form-group">
                <label>Đơn giao hàng *</label>
                <select name="orderId" class="form-control" required>
                    <option value="">-- Chọn đơn --</option>
                    <c:forEach var="o" items="${orders}">
                        <option value="${o.orderId}">#${o.orderId} - ${o.customerName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Kho nhập *</label>
                <select name="warehouseId" class="form-control" required>
                    <option value="">-- Chọn kho --</option>
                    <c:forEach var="w" items="${warehouses}">
                        <option value="${w.warehouseId}">${w.warehouseName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Lý do nhập</label>
                <select name="reason" class="form-control">
                    <option>Return</option>
                    <option>Damage</option>
                    <option>Customer Refuse</option>
                    <option>Wrong Item</option>
                    <option>Other</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">💾 Tạo phiếu nhập</button>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
