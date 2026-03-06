package controller;

import dao.AuditLogDAO;
import dao.DeliveryOrderDAO;
import dao.InboundDocumentDAO;
import dao.OutboundDocumentDAO;
import dao.ProductDAO;
import dao.StockLedgerDAO;
import dao.WarehouseDAO;
import model.InboundDocument;
import model.OutboundDocument;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WarehouseServlet — Servlet độc lập xử lý nghiệp vụ kho.
 * URL: /warehouse?action=xxx
 *
 * doGet : hiển thị danh sách, form outbound/inbound, stock ledger
 * doPost: lưu outbound, cập nhật outbound, lưu inbound
 */
@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {

    private final OutboundDocumentDAO outboundDAO  = new OutboundDocumentDAO();
    private final InboundDocumentDAO  inboundDAO   = new InboundDocumentDAO();
    private final StockLedgerDAO      stockDAO     = new StockLedgerDAO();
    private final DeliveryOrderDAO    orderDAO     = new DeliveryOrderDAO();
    private final WarehouseDAO        warehouseDAO = new WarehouseDAO();
    private final ProductDAO          productDAO   = new ProductDAO();
    private final AuditLogDAO         auditDAO     = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "outboundList": showOutboundList(req, resp); break;
            case "outboundForm": showOutboundForm(req, resp); break;
            case "inboundList":  showInboundList(req, resp);  break;
            case "inboundForm":  showInboundForm(req, resp);  break;
            case "stockLedger":  showStockLedger(req, resp);  break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "outboundSave":   handleOutboundSave(req, resp);   break;
            case "outboundUpdate": handleOutboundUpdate(req, resp); break;
            case "inboundSave":    handleInboundSave(req, resp);    break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ OUTBOUND ══════════════

    private void showOutboundList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("outbounds", outboundDAO.getAll());
        req.getRequestDispatcher("/views/outbound/outboundList.jsp").forward(req, resp);
    }

    private void showOutboundForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("orders",     orderDAO.getAll());
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.getRequestDispatcher("/views/outbound/outboundForm.jsp").forward(req, resp);
    }

    private void handleOutboundSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        OutboundDocument od = new OutboundDocument();
        od.setOrderId(Integer.parseInt(req.getParameter("orderId")));
        od.setWarehouseId(Integer.parseInt(req.getParameter("warehouseId")));
        od.setStatus(req.getParameter("status"));
        outboundDAO.insert(od);
        logAction(req, "INSERT", "OutboundDocuments", 0);
        resp.sendRedirect(req.getContextPath() + "/main?action=outboundList");
    }

    private void handleOutboundUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        OutboundDocument od = new OutboundDocument();
        od.setOutboundId(Integer.parseInt(req.getParameter("outboundId")));
        od.setStatus(req.getParameter("status"));
        outboundDAO.update(od);
        logAction(req, "UPDATE", "OutboundDocuments", od.getOutboundId());
        resp.sendRedirect(req.getContextPath() + "/main?action=outboundList");
    }

    // ══════════════ INBOUND ══════════════

    private void showInboundList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("inbounds", inboundDAO.getAll());
        req.getRequestDispatcher("/views/inbound/inboundList.jsp").forward(req, resp);
    }

    private void showInboundForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("orders",     orderDAO.getAll());
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.getRequestDispatcher("/views/inbound/inboundForm.jsp").forward(req, resp);
    }

    private void handleInboundSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        InboundDocument ind = new InboundDocument();
        ind.setOrderId(Integer.parseInt(req.getParameter("orderId")));
        ind.setWarehouseId(Integer.parseInt(req.getParameter("warehouseId")));
        ind.setReason(req.getParameter("reason"));
        inboundDAO.insert(ind);
        logAction(req, "INSERT", "InboundDocuments", 0);
        resp.sendRedirect(req.getContextPath() + "/main?action=inboundList");
    }

    // ══════════════ STOCK LEDGER ══════════════

    private void showStockLedger(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String warehouseId = req.getParameter("warehouseId");
        String productId   = req.getParameter("productId");
        String refType     = req.getParameter("refType");
        String fromDate    = req.getParameter("fromDate");
        String toDate      = req.getParameter("toDate");
        boolean hasFilter  = (warehouseId != null && !warehouseId.isEmpty())
                          || (productId   != null && !productId.isEmpty())
                          || (refType     != null && !refType.isEmpty());
        req.setAttribute("ledgers",    hasFilter
                ? stockDAO.search(warehouseId, productId, refType, fromDate, toDate)
                : stockDAO.getAll());
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.setAttribute("products",   productDAO.getAll());
        req.getRequestDispatcher("/views/stock/stockLedger.jsp").forward(req, resp);
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
