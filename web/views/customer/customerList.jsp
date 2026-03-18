<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>👥 Customer Management</h2>
        <a href="<%= request.getContextPath() %>/main?action=customerForm" class="btn btn-primary btn-sm">+ Add New</a>
    </div>
    <div class="card-body">
        <c:if test="${not empty error}"><div class="alert-box error">⚠️ ${error}</div></c:if>
        <form method="get" action="<%= request.getContextPath() %>/main" style="display:flex;gap:10px;margin-bottom:15px;">
            <input type="hidden" name="action" value="customerList">
            <input type="text" name="keyword" value="${keyword}" class="form-control" style="width:280px;" placeholder="Search by name, phone, email...">
            <button type="submit" class="btn btn-secondary">🔍 Search</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>#</th><th>Customer Name</th><th>Phone</th><th>Email</th><th>Address</th><th>Created At</th><th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${customers}">
                <tr>
                    <td>${c.customerId}</td>
                    <td>${c.customerName}</td>
                    <td>${c.phone}</td>
                    <td>${c.email}</td>
                    <td>${c.address}</td>
                    <td>${c.createdAt}</td>
                    <td>
                        <a href="<%= request.getContextPath() %>/main?action=customerForm&id=${c.customerId}" class="btn btn-warning btn-sm">Edit</a>
                        <a href="<%= request.getContextPath() %>/main?action=customerDelete&id=${c.customerId}"
                           class="btn btn-danger btn-sm" onclick="return confirm('Delete this customer?')">Delete</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty customers}">
                    <tr><td colspan="7" style="text-align:center;color:#aaa;">No data available</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>