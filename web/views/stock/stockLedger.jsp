<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>📊 Sổ cái Tồn kho</h2></div>
    <div class="card-body">
        <form method="get" action="<%= request.getContextPath() %>/main" style="display:flex;flex-wrap:wrap;gap:10px;margin-bottom:15px;">
            <input type="hidden" name="action" value="stockLedger">
            <select name="warehouseId" class="form-control" style="width:160px;">
                <option value="">Tất cả kho</option>
                <c:forEach var="w" items="${warehouses}"><option value="${w.warehouseId}">${w.warehouseName}</option></c:forEach>
            </select>
            <select name="productId" class="form-control" style="width:160px;">
                <option value="">Tất cả SP</option>
                <c:forEach var="p" items="${products}"><option value="${p.productId}">${p.productName}</option></c:forEach>
            </select>
            <select name="refType" class="form-control" style="width:120px;">
                <option value="">Loại</option>
                <option value="OUT">OUT</option>
                <option value="IN">IN</option>
            </select>
            <input type="date" name="fromDate" class="form-control" style="width:145px;">
            <input type="date" name="toDate"   class="form-control" style="width:145px;">
            <button type="submit" class="btn btn-secondary">🔍 Lọc</button>
        </form>
        <table>
            <thead><tr><th>#</th><th>Kho</th><th>SKU</th><th>Sản phẩm</th><th>Ref#</th><th>Loại</th><th>SL thay đổi</th><th>Tồn sau</th><th>Thời gian</th></tr></thead>
            <tbody>
                <c:forEach var="l" items="${ledgers}">
                <tr>
                    <td>${l.ledgerId}</td>
                    <td>${l.warehouseName}</td>
                    <td>${l.sku}</td>
                    <td>${l.productName}</td>
                    <td>${l.refId}</td>
                    <td><span class="badge ${l.refType=='OUT' ? 'badge-danger' : 'badge-success'}">${l.refType}</span></td>
                    <td style="text-align:right;color:${l.qtyChange < 0 ? '#c62828' : '#2e7d32'};">${l.qtyChange}</td>
                    <td style="text-align:right;font-weight:600;">${l.balanceAfter}</td>
                    <td>${l.createdAt}</td>
                </tr>
                </c:forEach>
                <c:if test="${empty ledgers}"><tr><td colspan="9" style="text-align:center;color:#aaa;">Không có dữ liệu</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
