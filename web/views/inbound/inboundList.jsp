<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>⬇️ Inbound Receipts (Returned Orders)</h2>
        <a href="<%= request.getContextPath() %>/main?action=inboundForm" class="btn btn-primary btn-sm">+ Create Inbound Receipt</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Order #</th><th>Customer</th><th>Warehouse</th><th>Inbound Date</th><th>Reason</th></tr></thead>
            <tbody>
                <c:forEach var="i" items="${inbounds}">
                <tr>
                    <td>${i.inboundId}</td>
                    <td>#${i.orderId}</td>
                    <td>${i.customerName}</td>
                    <td>${i.warehouseName}</td>
                    <td>${i.docDate}</td>
                    <td><span class="badge badge-warning">${i.reason}</span></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>