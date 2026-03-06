<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty product ? '➕ Thêm Sản phẩm' : '✏️ Sửa Sản phẩm'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=productList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body" style="max-width:600px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=productSave">
            <input type="hidden" name="productId" value="${product.productId}">
            <div class="form-row">
                <div class="form-group">
                    <label>SKU *</label>
                    <input type="text" name="sku" class="form-control" value="${product.sku}" required>
                </div>
                <div class="form-group">
                    <label>Tên sản phẩm *</label>
                    <input type="text" name="productName" class="form-control" value="${product.productName}" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Danh mục</label>
                    <input type="text" name="category" class="form-control" value="${product.category}">
                </div>
                <div class="form-group">
                    <label>Đơn vị</label>
                    <input type="text" name="unit" class="form-control" value="${product.unit}">
                </div>
                <div class="form-group">
                    <label>Đơn giá</label>
                    <input type="number" step="0.01" name="price" class="form-control" value="${product.price}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">💾 Lưu</button>
            <a href="<%= request.getContextPath() %>/main?action=productList" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
