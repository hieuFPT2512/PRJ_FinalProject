<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>🔍 Tìm kiếm Hóa đơn & Thanh toán</h2></div>
    <div class="card-body">
        <form method="get" action="<%= request.getContextPath() %>/main"
              style="display:flex;flex-wrap:wrap;gap:10px;margin-bottom:20px;padding:15px;background:#f8f9fa;border-radius:8px;">
            <input type="hidden" name="action" value="searchInvoices">
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
                <select name="status" class="form-control" style="width:120px;">
                    <option value="">Tất cả</option>
                    <option value="Unpaid"    ${param.status=='Unpaid'   ?'selected':''}>Unpaid</option>
                    <option value="Paid"      ${param.status=='Paid'     ?'selected':''}>Paid</option>
                    <option value="Cancelled" ${param.status=='Cancelled'?'selected':''}>Cancelled</option>
                </select>
            </div>
            <div>
                <label style="font-size:12px;font-weight:600;">Mã đơn #</label>
                <input type="number" name="orderId" class="form-control" style="width:100px;" value="${param.orderId}" placeholder="Order ID">
            </div>
            <div style="display:flex;align-items:flex-end;">
                <button type="submit" class="btn btn-primary">🔍 Tìm kiếm</button>
            </div>
        </form>

        <c:if test="${not empty invoices}">
            <p style="color:#555;margin-bottom:10px;">Tìm thấy <strong>${invoices.size()}</strong> hóa đơn</p>
            <table>
                <thead><tr><th>#</th><th>Đơn #</th><th>Khách hàng</th><th>Ngày xuất</th><th>Tổng tiền</th><th>Trạng thái</th><th></th></tr></thead>
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
                                <c:when test="${i.status=='Paid'}"><span class="badge badge-success">${i.status}</span></c:when>
                                <c:when test="${i.status=='Unpaid'}"><span class="badge badge-danger">${i.status}</span></c:when>
                                <c:otherwise><span class="badge badge-secondary">${i.status}</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="<%= request.getContextPath() %>/main?action=invoiceDetail&id=${i.invoiceId}" class="btn btn-secondary btn-sm">Chi tiết</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
