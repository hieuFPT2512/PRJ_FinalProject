<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách Người dùng</title>
    </head>
    <body>
        <div align="center">
            <h2>Quản lý Người dùng</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">Quay lại Dashboard</a>
            <hr>

            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên đăng nhập</th>
                        <th>Họ và tên</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.username}</td>
                            <td>${user.fullName}</td>
                            <td>${user.roleName}</td>
                            <td>${user.status}</td>
                            <td>${user.createdAt}</td>
                            <td align="center">
                                <form action="${pageContext.request.contextPath}/MainController" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="UpdateUserForm" />
                                    <input type="hidden" name="id" value="${user.userId}" />
                                    <button type="submit">Sửa</button>
                                </form>

                                <form action="${pageContext.request.contextPath}/MainController" method="post" style="display:inline;"
                                      onsubmit="return confirm('Xóa user ${user.username}?');">
                                    <input type="hidden" name="action" value="DeleteUser" />
                                    <input type="hidden" name="id" value="${user.userId}" />
                                    <button type="submit">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <br>
            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="AddUserForm" />
                <button type="submit">+ Thêm Người dùng mới</button>
            </form>
        </div>
    </body>
</html>