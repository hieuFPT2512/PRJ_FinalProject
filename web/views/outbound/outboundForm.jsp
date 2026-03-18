<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>➕ Create Outbound Receipt</h2>
        <a href="<%= request.getContextPath() %>/main?action=outboundList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=outboundSave">
            <div class="form-group">
                <label>Delivery Order *</label>
                <select name="orderId" class="form-control" required>
                    <option value="">-- Select order --</option>
                    <c:forEach var="o" items="${orders}">
                        <option value="${o.orderId}">#${o.orderId} - ${o.customerName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Warehouse *</label>
                <select name="warehouseId" class="form-control" required>
                    <option value="">-- Select warehouse --</option>
                    <c:forEach var="w" items="${warehouses}">
                        <option value="${w.warehouseId}">${w.warehouseName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Status</label>
                <select name="status" class="form-control">
                    <option value="Pending">Pending</option>
                    <option value="Approved">Approved</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">💾 Create Outbound Receipt</button>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>