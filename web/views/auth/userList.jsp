<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>👤 User Management</h2>
        <a href="<%= request.getContextPath() %>/main?action=userForm" class="btn btn-primary btn-sm">+ Add User</a>
    </div>
    <div class="card-body">
        <table>
            <thead><tr><th>#</th><th>Username</th><th>Full Name</th><th>Role</th><th>Status</th><th>Created At</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.userId}</td>
                    <td><strong>${u.username}</strong></td>
                    <td>${u.fullName}</td>
                    <td><span class="badge badge-info">${u.roleName}</span></td>
                    <td>
                        <c:choose>
                            <c:when test="${u.status=='Active'}"><span class="badge badge-success">${u.status}</span></c:when>
                            <c:otherwise><span class="badge badge-danger">${u.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>${u.createdAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=userForm&id=${u.userId}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="<%= request.getContextPath() %>/main?action=userDelete&id=${u.userId}" class="btn btn-danger btn-sm"
                           onclick="return confirm('Disable this user?')">Disable</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>