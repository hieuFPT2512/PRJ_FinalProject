<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>➕ Create New Shipment</h2>
        <a href="<%= request.getContextPath() %>/main?action=shipmentList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=shipmentSave">
            <div class="form-group">
                <label>Delivery Order *</label>
                <select name="orderId" class="form-control" required>
                    <option value="">-- Select order --</option>
                    <c:forEach var="o" items="${orders}">
                        <option value="${o.orderId}">#${o.orderId} - ${o.customerName} (${o.status})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Driver Name *</label>
                <input type="text" name="driverName" class="form-control" required placeholder="Driver name...">
            </div>
            <button type="submit" class="btn btn-primary">💾 Create Shipment</button>
            <a href="<%= request.getContextPath() %>/main?action=shipmentList" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>