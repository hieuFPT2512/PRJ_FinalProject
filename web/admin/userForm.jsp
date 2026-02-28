<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${(user != null && user.userId > 0) ? "Cập nhật Người dùng" : "Thêm Người dùng mới"}</title>
</head>
<body>

    <div align="center">
        <h2>
            <c:choose>
                <c:when test="${user != null && user.userId > 0}">Cập nhật thông tin User</c:when>
                <c:otherwise>Thêm Người dùng mới</c:otherwise>
            </c:choose>
        </h2>
        
        <c:if test="${not empty message}">
            <div style="padding: 10px; margin-bottom: 15px; border: 1px solid; width: 60%; 
                 ${alertType == 'success' ? 'color: green; background-color: #dff0d8; border-color: #d6e9c6;' 
                                          : 'color: red; background-color: #f2dede; border-color: #ebccd1;'}">
                <strong>${message}</strong>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/MainController" method="post">
            
            <c:choose>
                <c:when test="${user != null && user.userId > 0}">
                    <input type="hidden" name="action" value="UpdateUser" />
                    <input type="hidden" name="id" value="${user.userId}" />
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="action" value="AddUser" />
                </c:otherwise>
            </c:choose>

            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <td>Tên đăng nhập:</td>
                    <td>
                        <input type="text" name="username" value="${user.username}" size="40"
                               ${(user != null && user.userId > 0) ? 'readonly style="background-color: #e9ecef;"' : 'required'} />
                    </td>
                </tr>

                <tr>
                    <td>Mật khẩu:</td>
                    <td>
                        <input type="password" name="password" size="40" />
                        <c:if test="${user != null && user.userId > 0}">
                            <br><small><i>(Để trống nếu muốn giữ nguyên mật khẩu cũ)</i></small>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td>Họ và tên:</td>
                    <td>
                        <input type="text" name="fullName" value="${user.fullName}" size="40" required />
                    </td>
                </tr>

                <tr>
                    <td>Vai trò:</td>
                    <td>
                        <select name="roleId" style="width: 100%; padding: 5px;">
                            <option value="1" ${user.roleId == 1 ? 'selected' : ''}>Quản trị viên (Admin)</option>
                            <option value="2" ${user.roleId == 2 ? 'selected' : ''}>Kế toán (Accountant)</option>
                            <option value="3" ${user.roleId == 3 ? 'selected' : ''}>Thủ kho (Warehouse Staff)</option>
                            <option value="4" ${user.roleId == 4 ? 'selected' : ''}>Tài xế (Driver)</option>
                            <option value="5" ${user.roleId == 5 ? 'selected' : ''}>CSKH (Customer Service)</option>
                        </select>
                    </td>
                </tr>
                
                <c:if test="${user != null && user.userId > 0}">
                <tr>
                    <td>Trạng thái:</td>
                    <td>
                        <select name="status" style="width: 100%; padding: 5px;">
                            <option value="Active" ${user.status == 'Active' ? 'selected' : ''}>Hoạt động (Active)</option>
                            <option value="Inactive" ${user.status == 'Inactive' ? 'selected' : ''}>Đã khóa (Inactive)</option>
                        </select>
                    </td>
                </tr>
                </c:if>

                <tr>
                    <td colspan="2" align="center">
                        <button type="submit" style="padding: 10px 20px; font-weight: bold; cursor: pointer;">
                            <c:choose>
                                <c:when test="${user != null && user.userId > 0}">💾 Cập nhật</c:when>
                                <c:otherwise>✨ Lưu mới</c:otherwise>
                            </c:choose>
                        </button>
                    </td>
                </tr>
            </table>
        </form>
        
        <br>
        
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="UserList" />
            <button type="submit" style="cursor: pointer;">⬅️ Quay lại danh sách</button>
        </form>
        
    </div>

</body>
</html>