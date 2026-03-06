package controller;

import dao.CustomerDAO;
import dao.DeliveryOrderDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dao.StockLedgerDAO;
import dao.WarehouseDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SearchServlet — Servlet độc lập xử lý tìm kiếm nâng cao.
 * URL: /search?action=xxx
 *
 * doGet: searchOrders, searchStockDocs, searchInvoices (cả hiển thị form lẫn kết quả)
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private final DeliveryOrderDAO orderDAO     = new DeliveryOrderDAO();
    private final StockLedgerDAO   stockDAO     = new StockLedgerDAO();
    private final InvoiceDAO       invoiceDAO   = new InvoiceDAO();
    private final CustomerDAO      customerDAO  = new CustomerDAO();
    private final WarehouseDAO     warehouseDAO = new WarehouseDAO();
    private final ProductDAO       productDAO   = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "searchOrders":    handleSearchOrders(req, resp);    break;
            case "searchStockDocs": handleSearchStockDocs(req, resp); break;
            case "searchInvoices":  handleSearchInvoices(req, resp);  break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Search form submit qua POST → delegate sang doGet
        doGet(req, resp);
    }

    // ══════════════ SEARCH HANDLERS ══════════════

    private void handleSearchOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fromDate    = req.getParameter("fromDate");
        String toDate      = req.getParameter("toDate");
        String status      = req.getParameter("status");
        String warehouseId = req.getParameter("warehouseId");
        String customerId  = req.getParameter("customerId");
        String route       = req.getParameter("route");
        String hasCod      = req.getParameter("hasCod");

        if (fromDate != null && !fromDate.isEmpty()) {
            req.setAttribute("orders",
                    orderDAO.search(fromDate, toDate, status, warehouseId, customerId, route, hasCod));
        }
        req.setAttribute("customers",  customerDAO.getAll());
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.getRequestDispatcher("/views/search/searchOrders.jsp").forward(req, resp);
    }

    private void handleSearchStockDocs(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String warehouseId = req.getParameter("warehouseId");
        String productId   = req.getParameter("productId");
        String refType     = req.getParameter("refType");
        String fromDate    = req.getParameter("fromDate");
        String toDate      = req.getParameter("toDate");

        if (warehouseId != null && !warehouseId.isEmpty()) {
            req.setAttribute("ledgers",
                    stockDAO.search(warehouseId, productId, refType, fromDate, toDate));
        }
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.setAttribute("products",   productDAO.getAll());
        req.getRequestDispatcher("/views/search/searchStockDocs.jsp").forward(req, resp);
    }

    private void handleSearchInvoices(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fromDate = req.getParameter("fromDate");
        String toDate   = req.getParameter("toDate");
        String status   = req.getParameter("status");
        String orderId  = req.getParameter("orderId");

        boolean hasFilter = (fromDate != null && !fromDate.isEmpty())
                         || (status   != null && !status.isEmpty())
                         || (orderId  != null && !orderId.isEmpty());
        if (hasFilter) {
            req.setAttribute("invoices",
                    invoiceDAO.search(fromDate, toDate, status, orderId));
        }
        req.getRequestDispatcher("/views/search/searchInvoices.jsp").forward(req, resp);
    }
}
