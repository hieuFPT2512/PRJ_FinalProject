<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/header.jsp" %>
<div class="card">
    <div class="card-header">
        <h2>${empty customer ? '➕ Add Customer' : '✏️ Edit Customer'}</h2>
        <a href="<%= request.getContextPath() %>/main?action=customerList" class="btn btn-secondary btn-sm">← Back</a>
    </div>
    <div class="card-body" style="max-width:600px;">
        <form method="post" action="<%= request.getContextPath() %>/main?action=customerSave">
            <input type="hidden" name="customerId" value="${customer.customerId}">
            <div class="form-group">
                <label>Customer Name *</label>
                <input type="text" name="customerName" class="form-control" value="${customer.customerName}" required>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>Phone</label>
                    <input type="text" name="phone" class="form-control" value="${customer.phone}">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" value="${customer.email}">
                </div>
            </div>
            <div class="form-group">
                <label>Address</label>
                <input type="text" name="address" class="form-control" value="${customer.address}">
            </div>
            <button type="submit" class="btn btn-primary">💾 Save</button>
            <a href="<%= request.getContextPath() %>/main?action=customerList" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="/views/footer.jsp" %>