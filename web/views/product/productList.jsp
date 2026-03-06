<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📦 Quản lý Sản phẩm</h2>
        <a href="<%= request.getContextPath() %>/main?action=productForm" class="btn btn-primary btn-sm">+ Thêm mới</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>SKU</th><th>Tên sản phẩm</th><th>Danh mục</th><th>Đơn vị</th><th>Đơn giá</th><th>Thao tác</th></tr></thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.productId}</td><td>${p.sku}</td><td>${p.productName}</td>
                    <td>${p.category}</td><td>${p.unit}</td>
                    <td style="text-align:right;">${p.price}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=productForm&id=${p.productId}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="<%= request.getContextPath() %>/main?action=productDelete&id=${p.productId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa?')">Xóa</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
