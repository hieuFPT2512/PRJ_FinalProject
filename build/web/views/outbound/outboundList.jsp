<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>⬆️ Phiếu Xuất kho</h2>
        <a href="<%= request.getContextPath() %>/main?action=outboundForm" class="btn btn-primary btn-sm">+ Tạo phiếu xuất</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Đơn #</th><th>Khách hàng</th><th>Kho</th><th>Ngày xuất</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
            <tbody>
                <c:forEach var="o" items="${outbounds}">
                <tr>
                    <td>${o.outboundId}</td>
                    <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${o.orderId}">#${o.orderId}</a></td>
                    <td>${o.customerName}</td>
                    <td>${o.warehouseName}</td>
                    <td>${o.docDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${o.status == 'Approved'}"><span class="badge badge-success">${o.status}</span></c:when>
                            <c:when test="${o.status == 'Pending'}"><span class="badge badge-warning">${o.status}</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${o.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form method="post" action="<%= request.getContextPath() %>/main?action=outboundUpdate" style="display:inline-flex;gap:5px;">
                            <input type="hidden" name="outboundId" value="${o.outboundId}">
                            <select name="status" class="form-control" style="width:120px;padding:4px;">
                                <option value="Pending"   ${o.status=='Pending'  ?'selected':''}>Pending</option>
                                <option value="Approved"  ${o.status=='Approved' ?'selected':''}>Approved</option>
                                <option value="Closed"    ${o.status=='Closed'   ?'selected':''}>Closed</option>
                                <option value="Cancelled" ${o.status=='Cancelled'?'selected':''}>Cancelled</option>
                            </select>
                            <button type="submit" class="btn btn-warning btn-sm">Cập nhật</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
