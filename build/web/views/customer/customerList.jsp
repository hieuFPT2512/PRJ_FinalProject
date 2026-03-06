<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>👥 Quản lý Khách hàng</h2>
        <a href="<%= request.getContextPath() %>/main?action=customerForm" class="btn btn-primary btn-sm">+ Thêm mới</a>
    </div>
    <div class="card-body">
        <form method="get" action="<%= request.getContextPath() %>/main" style="display:flex;gap:10px;margin-bottom:15px;">
            <input type="hidden" name="action" value="customerList">
            <input type="text" name="keyword" value="${keyword}" class="form-control" style="width:280px;" placeholder="Tìm theo tên, SĐT, email...">
            <button type="submit" class="btn btn-secondary">🔍 Tìm</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>#</th><th>Tên khách hàng</th><th>SĐT</th><th>Email</th><th>Địa chỉ</th><th>Ngày tạo</th><th>Thao tác</th>
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
                        <a href="<%= request.getContextPath() %>/main?action=customerForm&id=${c.customerId}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="<%= request.getContextPath() %>/main?action=customerDelete&id=${c.customerId}"
                           class="btn btn-danger btn-sm" onclick="return confirm('Xóa khách hàng này?')">Xóa</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty customers}">
                    <tr><td colspan="7" style="text-align:center;color:#aaa;">Không có dữ liệu</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>
