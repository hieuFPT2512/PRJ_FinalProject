<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty user ? '➕ Add User' : '✏️ Edit User'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=userList" class="btn btn-secondary btn-sm">← Back</a>
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
                <label>Password *</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            </c:if>
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName" class="form-control" value="${user.fullName}">
            </div>
            <div class="form-group">
                <label>Role *</label>
                <select name="roleId" class="form-control" required>
                    <option value="">-- Select role --</option>
                    <c:forEach var="r" items="${roles}">
                        <option value="${r.roleId}" ${user.roleId == r.roleId ? 'selected' : ''}>${r.roleName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Status</label>
                <select name="status" class="form-control">
                    <option value="Active"   ${user.status=='Active'   ? 'selected' : ''}>Active</option>
                    <option value="Inactive" ${user.status=='Inactive' ? 'selected' : ''}>Inactive</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">💾 Save</button>
            <a href="<%= request.getContextPath() %>/main?action=userList" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>