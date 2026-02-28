<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<h3>Khu vực làm việc Kế toán - ${sessionScope.USER_INFO.fullName}</h3>
<ul>
    <li><b>Đối soát & Cảnh báo (Quan trọng):</b>
        <ul>
            <li><a href="../search/reconcileWorkbench.jsp">Bàn làm việc Đối soát (Reconcile Workbench)</a></li>
            <li><a href="../risk/alertsList.jsp">Danh sách Cảnh báo rủi ro</a></li>
            <li><a href="../risk/caseList.jsp">Xử lý sai lệch (Cases)</a></li>
        </ul>
    </li>
    <li><b>Tài chính & Thu hộ:</b>
        <ul>
            <li><a href="invoiceList.jsp">Quản lý Hóa đơn</a></li>
            <li><a href="codReconcile.jsp">Đối soát tiền COD (Shipper nộp tiền)</a></li>
            <li><a href="paymentList.jsp">Lịch sử thanh toán</a></li>
        </ul>
    </li>
</ul>
<a href="../logout">Đăng xuất</a>