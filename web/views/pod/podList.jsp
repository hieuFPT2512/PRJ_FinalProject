<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📸 Bằng chứng giao hàng (POD)</h2>
        <a href="<%= request.getContextPath() %>/main?action=podUpload" class="btn btn-primary btn-sm">+ Upload POD</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Lô #</th><th>Đơn #</th><th>Tài xế</th><th>Người ký</th><th>Ảnh</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
            <tbody>
                <c:forEach var="p" items="${pods}">
                <tr>
                    <td>${p.podId}</td>
                    <td>${p.shipmentId}</td>
                    <td>${p.orderId}</td>
                    <td>${p.driverName}</td>
                    <td>${p.signedBy}</td>
                    <td>${p.imageUrl}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.status == 'Completed'}"><span class="badge badge-success">${p.status}</span></c:when>
                            <c:when test="${p.status == 'Pending'}"><span class="badge badge-warning">${p.status}</span></c:when>
                            <c:otherwise><span class="badge badge-danger">${p.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${p.status == 'Pending'}">
                        <form method="post" action="<%= request.getContextPath() %>/main?action=podConfirm" style="display:inline;">
                            <input type="hidden" name="podId" value="${p.podId}">
                            <input type="hidden" name="signedBy" value="${p.signedBy}">
                            <button type="submit" class="btn btn-success btn-sm">✔ Xác nhận</button>
                        </form>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
