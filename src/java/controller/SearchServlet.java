package controller;

import dao.CustomerDAO;
import dao.DeliveryOrderDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dao.StockLedgerDAO;
import dao.WarehouseDAO;
import model.User;
import utils.AuthUtils;
import utils.RoleConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Phân quyền SearchServlet:
 *   searchOrders    → ADMIN, WAREHOUSE_STAFF, DRIVER, CUSTOMER_SERVICE
 *   searchStockDocs → ADMIN, WAREHOUSE_STAFF
 *   searchInvoices  → ADMIN, ACCOUNTANT
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private final DeliveryOrderDAO orderDAO     = new DeliveryOrderDAO();
    private final StockLedgerDAO   stockDAO     = new StockLedgerDAO();
    private final InvoiceDAO       invoiceDAO   = new InvoiceDAO();
    private final CustomerDAO      customerDAO  = new CustomerDAO();
    private final WarehouseDAO     warehouseDAO = new WarehouseDAO();
    private final ProductDAO       productDAO   = new ProductDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // Tìm kiếm đơn hàng: mọi role trừ Kế toán
            case "searchOrders":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                String fromDate1    = request.getParameter("fromDate");
                String toDate1      = request.getParameter("toDate");
                String status1      = request.getParameter("status");
                String warehouseId1 = request.getParameter("warehouseId");
                String customerId1  = request.getParameter("customerId");
                String route1       = request.getParameter("route");
                String hasCod1      = request.getParameter("hasCod");
                if (fromDate1 != null && !fromDate1.isEmpty()) {
                    request.setAttribute("orders",
                            orderDAO.search(fromDate1, toDate1, status1, warehouseId1, customerId1, route1, hasCod1));
                }
                request.setAttribute("customers",  customerDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.getRequestDispatcher("/views/search/searchOrders.jsp").forward(request, response);
                break;

            // Tìm kiếm chứng từ kho: chỉ ADMIN và Thủ kho
            case "searchStockDocs":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                String warehouseId2 = request.getParameter("warehouseId");
                String productId2   = request.getParameter("productId");
                String refType2     = request.getParameter("refType");
                String fromDate2    = request.getParameter("fromDate");
                String toDate2      = request.getParameter("toDate");
                if (warehouseId2 != null && !warehouseId2.isEmpty()) {
                    request.setAttribute("ledgers",
                            stockDAO.search(warehouseId2, productId2, refType2, fromDate2, toDate2));
                }
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.setAttribute("products",   productDAO.getAll());
                request.getRequestDispatcher("/views/search/searchStockDocs.jsp").forward(request, response);
                break;

            // Tìm kiếm hóa đơn: chỉ ADMIN và Kế toán
            case "searchInvoices":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_ACCOUNTANT)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                String fromDate3 = request.getParameter("fromDate");
                String toDate3   = request.getParameter("toDate");
                String status3   = request.getParameter("status");
                String orderId3  = request.getParameter("orderId");
                boolean hasFilter = (fromDate3 != null && !fromDate3.isEmpty())
                                 || (status3   != null && !status3.isEmpty())
                                 || (orderId3  != null && !orderId3.isEmpty());
                if (hasFilter) {
                    request.setAttribute("invoices",
                            invoiceDAO.search(fromDate3, toDate3, status3, orderId3));
                }
                request.getRequestDispatcher("/views/search/searchInvoices.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/main?action=dashboard");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}
