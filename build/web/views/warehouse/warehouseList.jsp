<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🏭 Quản lý Kho hàng</h2>
        <a href="<%= request.getContextPath() %>/main?action=warehouseForm" class="btn btn-primary btn-sm">+ Thêm mới</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Tên kho</th><th>Địa chỉ</th><th>Quản lý kho</th><th>Ngày tạo</th><th>Thao tác</th></tr></thead>
            <tbody>
                <c:forEach var="w" items="${warehouses}">
                <tr>
                    <td>${w.warehouseId}</td><td>${w.warehouseName}</td><td>${w.location}</td>
                    <td>${w.manager}</td><td>${w.createdAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=warehouseForm&id=${w.warehouseId}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="<%= request.getContextPath() %>/main?action=warehouseDelete&id=${w.warehouseId}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa kho này?')">Xóa</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
