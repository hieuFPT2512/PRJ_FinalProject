<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🧾 Invoice Management</h2></div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Order #</th><th>Customer</th><th>Issue Date</th><th>Total Amount</th><th>Status</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="i" items="${invoices}">
                <tr>
                    <td>${i.invoiceId}</td>
                    <td>#${i.orderId}</td>
                    <td>${i.customerName}</td>
                    <td>${i.issueDate}</td>
                    <td style="text-align:right;font-weight:600;">${i.totalAmount}</td>
                    <td>
                        <c:choose>
                            <c:when test="${i.status == 'Paid'}"><span class="badge badge-success">${i.status}</span></c:when>
                            <c:when test="${i.status == 'Unpaid'}"><span class="badge badge-danger">${i.status}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${i.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=invoiceDetail&id=${i.invoiceId}" class="btn btn-secondary btn-sm">Detail</a>
                        <form method="post" action="<%= request.getContextPath() %>/main?action=invoiceUpdate" style="display:inline;">
                            <input type="hidden" name="invoiceId" value="${i.invoiceId}">
                            <input type="hidden" name="status" value="Paid">
                            <c:if test="${i.status == 'Unpaid'}">
                                <button type="submit" class="btn btn-success btn-sm">✔ Mark as Paid</button>
                            </c:if>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>