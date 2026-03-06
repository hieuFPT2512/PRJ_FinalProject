<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🔍 Tìm kiếm Đơn giao hàng</h2></div>
    <div class="card-body">
        <form method="get" action="<%= request.getContextPath() %>/main"
              style="display:flex;flex-wrap:wrap;gap:10px;margin-bottom:20px;padding:15px;background:#f8f9fa;border-radius:8px;">
            <input type="hidden" name="action" value="searchOrders">
            <div>
                <label style="font-size:12px;font-weight:600;">Từ ngày</label>
                <input type="date" name="fromDate" class="form-control" style="width:145px;" value="${param.fromDate}">
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Đến ngày</label>
                <input type="date" name="toDate" class="form-control" style="width:145px;" value="${param.toDate}">
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Trạng thái</label>
                <select name="status" class="form-control" style="width:130px;">
                    <option value="">Tất cả</option>
                    <option value="Pending"   ${param.status=='Pending'  ?'selected':''}>Pending</option>
                    <option value="Shipping"  ${param.status=='Shipping' ?'selected':''}>Shipping</option>
                    <option value="Delivered" ${param.status=='Delivered'?'selected':''}>Delivered</option>
                    <option value="Cancelled" ${param.status=='Cancelled'?'selected':''}>Cancelled</option>
                </select>
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Khách hàng</label>
                <select name="customerId" class="form-control" style="width:160px;">
                    <option value="">Tất cả</option>
                    <c:forEach var="c" items="${customers}">
                        <option value="${c.customerId}" ${param.customerId==c.customerId?'selected':''}>${c.customerName}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Kho</label>
                <select name="warehouseId" class="form-control" style="width:140px;">
                    <option value="">Tất cả kho</option>
                    <c:forEach var="w" items="${warehouses}">
                        <option value="${w.warehouseId}">${w.warehouseName}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Tuyến</label>
                <input type="text" name="route" class="form-control" style="width:100px;" value="${param.route}" placeholder="VD: HN">
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">COD</label>
                <select name="hasCod" class="form-control" style="width:100px;">
                    <option value="">Tất cả</option>
                    <option value="Y">Có COD</option>
                    <option value="N">Không</option>
                </select>
            </div>
            <div style="display:flex;align-items:flex-end;">
                <button type="submit" class="btn btn-primary">🔍 Tìm kiếm</button>
            </div>
        </form>

        <c:if test="${not empty orders}">
            <p style="color:#555;margin-bottom:10px;">Tìm thấy <strong>${orders.size()}</strong> kết quả</p>
            <table>
                <thead><tr><th>#</th><th>Khách hàng</th><th>Kho</th><th>Tuyến</th><th>COD</th><th>Tổng tiền</th><th>Trạng thái</th><th>Ngày tạo</th><th>Thao tác</th></tr></thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.orderId}</td>
                        <td>${o.customerName}</td>
                        <td>${o.warehouseName}</td>
                        <td>${o.route}</td>
                        <td>${o.hasCod == 'Y' ? 'Có' : 'Không'}</td>
                        <td style="text-align:right;">${o.totalAmount}</td>
                        <td><span class="badge badge-info">${o.status}</span></td>
                        <td>${o.orderDate}</td>
                        <td><a href="<%= request.getContextPath() %>/main?action=orderDetail&id=${o.orderId}" class="btn btn-secondary btn-sm">Chi tiết</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty orders && param.fromDate != null}">
            <div class="alert-box warning">Không tìm thấy kết quả phù hợp.</div>
        </c:if>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
