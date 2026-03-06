<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>💳 Quản lý Thanh toán</h2></div>
    <div class="card-body">
        <h3 style="color:#1e3a5f;margin-bottom:12px;">Ghi nhận thanh toán mới</h3>
        <form method="post" action="<%= request.getContextPath() %>/main?action=paymentSave"
              style="display:flex;gap:10px;flex-wrap:wrap;margin-bottom:20px;padding:15px;background:#f8f9fa;border-radius:8px;">
            <div style="flex:1;min-width:180px;">
                <label style="font-weight:600;font-size:13px;">Hóa đơn</label>
                <select name="invoiceId" class="form-control" required>
                    <option value="">-- Chọn hóa đơn --</option>
                    <c:forEach var="i" items="${invoices}">
                        <c:if test="${i.status == 'Unpaid'}">
                            <option value="${i.invoiceId}">#${i.invoiceId} - ${i.customerName} (${i.totalAmount})</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div style="flex:0 0 130px;">
                <label style="font-weight:600;font-size:13px;">Số tiền</label>
                <input type="number" step="0.01" name="amount" class="form-control" required>
            </div>
            <div style="flex:0 0 130px;">
                <label style="font-weight:600;font-size:13px;">Phương thức</label>
                <select name="method" class="form-control">
                    <option value="COD">COD</option>
                    <option value="Bank">Bank</option>
                    <option value="Cash">Cash</option>
                </select>
            </div>
            <div style="display:flex;align-items:flex-end;">
                <button type="submit" class="btn btn-success">💾 Ghi nhận</button>
            </div>
        </form>

        <h3 style="color:#1e3a5f;margin-bottom:10px;">Lịch sử thanh toán</h3>
        <table>
            <thead><tr><th>#</th><th>HĐ #</th><th>Đơn #</th><th>Khách hàng</th><th>Số tiền</th><th>Phương thức</th><th>Ngày TT</th></tr></thead>
            <tbody>
                <c:forEach var="p" items="${payments}">
                <tr>
                    <td>${p.paymentId}</td>
                    <td>${p.invoiceId}</td>
                    <td>${p.orderId}</td>
                    <td>${p.customerName}</td>
                    <td style="text-align:right;font-weight:600;">${p.amount}</td>
                    <td><span class="badge badge-info">${p.method}</span></td>
                    <td>${p.paidDate}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
