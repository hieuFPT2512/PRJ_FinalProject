<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
    </head>
    <body>

        <div align="center">
            <h2>Admin Dashboard</h2>
            <p>Xin chào: <b>${sessionScope.USER_INFO.fullName}</b></p>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            <hr>
        </div>

        <div align="center">

            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="UserList">
                <button type="submit">Quản lý Tài khoản (Users)</button>
            </form>
            <br>

            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="RoleList">
                <button type="submit">Quản lý Phân quyền (Roles)</button>
            </form>
            <br>
            
            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="CustomerList">
                <button type="submit">Quản lý Khách hàng (Customers)</button>
            </form>
            <br>
            
            <form action="${pageContext.request.contextPath}/MainController" method="post">
                <input type="hidden" name="action" value="AuditLog">
                <button type="submit">Nhật ký hệ thống (Audit Log)</button>
            </form>
            <br>



            <form action="${pageContext.request.contextPath}/MainController