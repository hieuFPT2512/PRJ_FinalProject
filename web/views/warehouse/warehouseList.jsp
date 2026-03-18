<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>🏭 Warehouse Management</h2>
        <a href="<%= request.getContextPath() %>/main?action=warehouseForm" class="btn btn-primary btn-sm">+ Add New</a>
    </div>
    <div class="card-body">
        <c:if test="${not empty error}"><div class="alert-box error">⚠️ ${error}</div></c:if>
        <table>
            <thead><tr><th>#</th><th>Warehouse Name</th><th>Location</th><th>Warehouse Manager</th><th>Created At</th><th>Action</th></tr></thead>
            <tbody>
                <c:forEach var="w" items="${warehouses}">
                <tr>
                    <td>${w.warehouseId}</td><td>${w.warehouseName}</td><td>${w.location}</td>
                    <td>${w.manager}</td><td>${w.createdAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=warehouseForm&id=${w.warehouseId}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="<%= request.getContextPath() %>/main?action=warehouseDelete&id=${w.warehouseId}" class="btn btn-danger btn-sm" onclick="return confirm('Delete this warehouse?')">Delete</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>