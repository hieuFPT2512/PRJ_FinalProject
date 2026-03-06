<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty warehouse ? '➕ Thêm Kho' : '✏️ Sửa Kho'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=warehouseList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=warehouseSave">
            <input type="hidden" name="warehouseId" value="${warehouse.warehouseId}">
            <div class="form-group">
                <label>Tên kho *</label>
                <input type="text" name="warehouseName" class="form-control" value="${warehouse.warehouseName}" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="location" class="form-control" value="${warehouse.location}">
            </div>
            <div class="form-group">
                <label>Quản lý kho</label>
                <input type="text" name="manager" class="form-control" value="${warehouse.manager}">
            </div>
            <button type="submit" class="btn btn-primary">💾 Lưu</button>
            <a href="<%= request.getContextPath() %>/main?action=warehouseList" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
