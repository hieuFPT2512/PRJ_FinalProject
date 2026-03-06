<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>⬇️ Phiếu Nhập kho (Hoàn hàng)</h2>
        <a href="<%= request.getContextPath() %>/main?action=inboundForm" class="btn btn-primary btn-sm">+ Tạo phiếu nhập</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Đơn #</th><th>Khách hàng</th><th>Kho</th><th>Ngày nhập</th><th>Lý do</th></tr></thead>
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
