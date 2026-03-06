<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty user ? '➕ Thêm Người dùng' : '✏️ Sửa Người dùng'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=userList" class="btn btn-secondary btn-sm">← Quay lại</a>
    </div>
    <div class="card-body" style="max-width:500px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=userSave">
            <input type="hidden" name="userId" value="${user.userId}">
            <c:if test="${empty user}">
            <div class="form-group">
                <label>Username *</label>
                <input type="text" name="username" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Mật khẩu *</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            </c:if>
            <div class="form-group">
                <label>Họ tên</label>
                <input type="text" name="fullName" class="form-control" value="${user.fullName}">
            </div>
            <div class="form-group">
                <label>Vai trò *</label>
                <select name="roleId" class="form-control" required>
                    <option value="">-- Chọn vai trò --</option>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r.roleId}" ${user.roleId == r.roleId ? 'selected' : ''}>${r.roleName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <select name="status" class="form-control">
                    <option value="Active"   ${user.status=='Active'   ? 'selected' : ''}>Active</option>
                    <option value="Inactive" ${user.status=='Inactive' ? 'selected' : ''}>Inactive</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">💾 Lưu</button>
            <a href="<%= request.getContextPath() %>/main?action=userList" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
