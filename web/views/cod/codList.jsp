<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>💵 COD Reconciliation</h2></div>
    <div class="card-body">
        <table>
            <thead>
                <tr><th>#</th><th>Order #</th><th>Customer</th><th>Expected COD</th><th>Received COD</th><th>Difference</th><th>Status</th><th>Update</th></tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${cods}">
                <tr>
                    <td>${c.codId}</td>
                    <td>#${c.orderId}</td>
                    <td>${c.customerName}</td>
                    <td style="text-align:right;">${c.expectedCod}</td>
                    <td style="text-align:right;">${c.receivedCod}</td>
                    <td style="text-align:right;color:${c.mismatchAmount != 0 ? '#c62828' : '#2e7d32'};font-weight:600;">
                        ${c.mismatchAmount}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${c.status == 'OK'}"><span class="badge badge-success">${c.status}</span></c:when>
                            <c:when test="${c.status == 'Mismatch'}"><span class="badge badge-danger">${c.status}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${c.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form method="post" action="<%= request.getContextPath() %>/main?action=codUpdate" style="display:inline-flex;gap:5px;">
                            <input type="hidden" name="codId" value="${c.codId}">
                            <input type="number" step="0.01" name="receivedCod" value="${c.receivedCod}" class="form-control" style="width:120px;padding:4px;">
                            <select name="status" class="form-control" style="width:100px;padding:4px;">
                                <option value="OK"       ${c.status=='OK'      ?'selected':''}>OK</option>
                                <option value="Mismatch" ${c.status=='Mismatch'?'selected':''}>Mismatch</option>
                                <option value="No COD"   ${c.status=='No COD'  ?'selected':''}>No COD</option>
                            </select>
                            <button type="submit" class="btn btn-warning btn-sm">Update</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>