<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header"><h2>📊 Inventory Ledger</h2></div>
    <div class="card-body">
        <form method="get" action="<%= request.getContextPath() %>/main" style="display:flex;flex-wrap:wrap;gap:10px;margin-bottom:15px;">
            <input type="hidden" name="action" value="stockLedger">
            <select name="warehouseId" class="form-control" style="width:160px;">
                <option value="">All Warehouses</option>
                <c:forEach var="w" items="${warehouses}"><option value="${w.warehouseId}">${w.warehouseName}</option></c:forEach>
            </select>
            <select name="productId" class="form-control" style="width:160px;">
                <option value="">All Products</option>
                <c:forEach var="p" items="${products}"><option value="${p.productId}">${p.productName}</option></c:forEach>
            </select>
            <select name="refType" class="form-control" style="width:120px;">
                <option value="">Type</option>
                <option value="OUTBOUND">OUTBOUND</option>
                <option value="INBOUND">INBOUND</option>
            </select>
            <input type="date" name="fromDate" class="form-control" style="width:145px;">
            <input type="date" name="toDate"   class="form-control" style="width:145px;">
            <button type="submit" class="btn btn-secondary">🔍 Filter</button>
        </form>
        <table>
            <thead><tr><th>#</th><th>Warehouse</th><th>SKU</th><th>Product</th><th>Ref#</th><th>Type</th><th>Qty Change</th><th>Balance After</th><th>Created At</th></tr></thead>
            <tbody>
                <c:forEach var="l" items="${ledgers}">
                <tr>
                    <td>${l.ledgerId}</td>
                    <td>${l.warehouseName}</td>
                    <td>${l.sku}</td>
                    <td>${l.productName}</td>
                    <td>${l.refId}</td>
                    <td>
                        <c:choose>
                            <c:when test="${l.refType == 'OUTBOUND'}"><span class="badge badge-outbound">OUTBOUND</span></c:when>
                            <c:when test="${l.refType == 'INBOUND'}"><span class="badge badge-inbound">INBOUND</span></c:when>
                            <c:otherwise><span class="badge badge-secondary">${l.refType}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td style="text-align:right;font-weight:600;color:${l.qtyChange < 0 ? '#c62828' : '#2e7d32'};">${l.qtyChange}</td>
                    <td style="text-align:right;font-weight:600;">${l.balanceAfter}</td>
                    <td>${l.createdAt}</td>
                </tr>
                </c:forEach>
                <c:if test="${empty ledgers}"><tr><td colspan="9" style="text-align:center;color:#aaa;">No data available</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
