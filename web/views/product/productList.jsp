<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>📦 Product Management</h2>
        <a href="<%= request.getContextPath()%>/main?action=productForm" class="btn btn-primary btn-sm">+ Add New</a>
    </div>
    <div class="card-body">
        <c:if test="${not empty error}"><div class="alert-box error">⚠️ ${error}</div></c:if>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>SKU</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Unit</th>
                        <th>Warehouse</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.productId}</td>
                        <td>${p.sku}</td>
                        <td>${p.productName}</td>
                        <td>${p.category}</td>
                        <td>${p.unit}</td>

                        <td>
                            <c:choose>
                                <c:when test="${not empty p.warehouseName}">
                                    ${p.warehouseName}
                                </c:when>
                                <c:otherwise>
                                    Distribution Center
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align:center;">${p.quantity}</td>

                        <td style="text-align:right;">${p.price}</td>

                        <td>
                            <a href="<%= request.getContextPath()%>/main?action=productForm&id=${p.productId}" class="btn btn-warning btn-sm">Edit</a>
                            <a href="<%= request.getContextPath()%>/main?action=productDelete&id=${p.productId}" class="btn btn-danger btn-sm" onclick="return confirm('Delete?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>