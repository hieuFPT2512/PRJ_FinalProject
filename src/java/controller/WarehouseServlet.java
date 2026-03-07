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
import utils.AuthUtils;
import utils.RoleConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Phân quyền WarehouseServlet:
 *   Tất cả action → CHỈ ADMIN (1) và WAREHOUSE_STAFF (3)
 *   (Kế toán, Tài xế, CSKH không có quyền vào kho)
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        // Toàn bộ nghiệp vụ kho: chỉ ADMIN và WAREHOUSE_STAFF
        if (!AuthUtils.hasRole(loginUser,
                RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_WAREHOUSE_STAFF)) {
            AuthUtils.denyAccess(request, response); return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ XUẤT KHO ══════════════
            case "outboundList":
                request.setAttribute("outbounds", outboundDAO.getAll());
                request.getRequestDispatcher("/views/outbound/outboundList.jsp").forward(request, response);
                break;

            case "outboundForm":
                request.setAttribute("orders",     orderDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.getRequestDispatcher("/views/outbound/outboundForm.jsp").forward(request, response);
                break;

            case "outboundSave":
                OutboundDocument od = new OutboundDocument();
                od.setOrderId(Integer.parseInt(request.getParameter("orderId")));
                od.setWarehouseId(Integer.parseInt(request.getParameter("warehouseId")));
                od.setStatus(request.getParameter("status"));
                outboundDAO.insert(od);
                logAction(request, "INSERT", "OutboundDocuments", 0);
                response.sendRedirect(request.getContextPath() + "/main?action=outboundList");
                break;

            case "outboundUpdate":
                OutboundDocument ou = new OutboundDocument();
                ou.setOutboundId(Integer.parseInt(request.getParameter("outboundId")));
                ou.setStatus(request.getParameter("status"));
                outboundDAO.update(ou);
                logAction(request, "UPDATE", "OutboundDocuments", ou.getOutboundId());
                response.sendRedirect(request.getContextPath() + "/main?action=outboundList");
                break;

            // ══════════════ NHẬP KHO ══════════════
            case "inboundList":
                request.setAttribute("inbounds", inboundDAO.getAll());
                request.getRequestDispatcher("/views/inbound/inboundList.jsp").forward(request, response);
                break;

            case "inboundForm":
                request.setAttribute("orders",     orderDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.getRequestDispatcher("/views/inbound/inboundForm.jsp").forward(request, response);
                break;

            case "inboundSave":
                InboundDocument ind = new InboundDocument();
                ind.setOrderId(Integer.parseInt(request.getParameter("orderId")));
                ind.setWarehouseId(Integer.parseInt(request.getParameter("warehouseId")));
                ind.setReason(request.getParameter("reason"));
                inboundDAO.insert(ind);
                logAction(request, "INSERT", "InboundDocuments", 0);
                response.sendRedirect(request.getContextPath() + "/main?action=inboundList");
                break;

            // ══════════════ SỔ CÁI TỒN KHO ══════════════
            case "stockLedger":
                String warehouseId = request.getParameter("warehouseId");
                String productId   = request.getParameter("productId");
                String refType     = request.getParameter("refType");
                String fromDate    = request.getParameter("fromDate");
                String toDate      = request.getParameter("toDate");
                boolean hasFilter  = (warehouseId != null && !warehouseId.isEmpty())
                                  || (productId   != null && !productId.isEmpty())
                                  || (refType     != null && !refType.isEmpty());
                request.setAttribute("ledgers", hasFilter
                        ? stockDAO.search(warehouseId, productId, refType, fromDate, toDate)
                        : stockDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.setAttribute("products",   productDAO.getAll());
                request.getRequestDispatcher("/views/stock/stockLedger.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/main?action=dashboard");
        }
    }

    private void logAction(HttpServletRequest request, String action, String table, int id) {
        User user = (User) request.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}
