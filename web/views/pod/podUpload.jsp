<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📤 Upload POD</h2>
        <a href="<%= request.getContextPath() %>/main?action=podList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=podUpload">
            <div class="form-group">
                <label>Shipment *</label>
                <select name="shipmentId" class="form-control" required>
                    <option value="">-- Select shipment --</option>
                    <c:forEach var="s" items="${shipments}">
                        <option value="${s.shipmentId}">#${s.shipmentId} - ${s.driverName} (${s.status})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Receiver Signature</label>
                <input type="text" name="signedBy" class="form-control" placeholder="Receiver name...">
            </div>
            <div class="form-group">
                <label>POD Image URL</label>
                <input type="text" name="imageUrl" class="form-control" placeholder="pod_filename.jpg or URL...">
            </div>
            <button type="submit" class="btn btn-primary">💾 Save POD</button>
            <a href="<%= request.getContextPath() %>/main?action=podList" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>