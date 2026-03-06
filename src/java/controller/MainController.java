package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MainController — Front Controller duy nhất.
 *
 * Dùng forward (KHÔNG phải redirect) để chuyển đến Servlet con.
 * Forward giữ nguyên request trong server → session luôn tồn tại,
 * AuthFilter chỉ chặn 1 lần duy nhất tại điểm vào.
 *
 * Luồng:
 *   Browser → GET/POST /main?action=xxx
 *                  ↓  forward (server-side, browser không biết)
 *             AuthServlet / DeliveryServlet / ... (xử lý và forward đến JSP)
 */
@WebServlet("/main")
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) action = "dashboard";

        String target = resolveTarget(action);
        // forward nội bộ — không tạo HTTP request mới, session được giữ nguyên
        req.getRequestDispatcher(target).forward(req, resp);
    }

    /**
     * Ánh xạ action → path của Servlet con.
     * Servlet con sẽ nhận đúng action param từ request gốc.
     */
    private String resolveTarget(String action) {
        switch (action) {
            case "dashboard": return "/views/dashboard.jsp";

            // ── Auth ──────────────────────────────────────────
            case "login":
            case "logout":
            case "userList":
            case "userForm":
            case "userSave":
            case "userDelete":
            case "auditLog":
                return "/auth";

            // ── Master Data ───────────────────────────────────
            case "customerList":
            case "customerForm":
            case "customerSave":
            case "customerDelete":
            case "productList":
            case "productForm":
            case "productSave":
            case "productDelete":
            case "warehouseList":
            case "warehouseForm":
            case "warehouseSave":
            case "warehouseDelete":
                return "/masterdata";

            // ── Delivery ──────────────────────────────────────
            case "orderList":
            case "orderForm":
            case "orderDetail":
            case "orderSave":
            case "orderDelete":
            case "shipmentList":
            case "shipmentForm":
            case "shipmentSave":
            case "shipmentUpdate":
            case "podList":
            case "podUpload":
            case "podConfirm":
                return "/delivery";

            // ── Warehouse ─────────────────────────────────────
            case "outboundList":
            case "outboundForm":
            case "outboundSave":
            case "outboundUpdate":
            case "inboundList":
            case "inboundForm":
            case "inboundSave":
            case "stockLedger":
                return "/warehouse";

            // ── Accounting ────────────────────────────────────
            case "invoiceList":
            case "invoiceDetail":
            case "invoiceUpdate":
            case "paymentList":
            case "paymentSave":
            case "codList":
            case "codUpdate":
                return "/accounting";

            // ── Search ────────────────────────────────────────
            case "searchOrders":
            case "searchStockDocs":
            case "searchInvoices":
                return "/search";

            // ── Alert & Case ──────────────────────────────────
            case "alertList":
            case "alertDetail":
            case "alertClose":
            case "caseList":
            case "caseDetail":
            case "caseAssign":
            case "caseClose":
                return "/alert";

            default:
                return "/views/dashboard.jsp";
        }
    }
}
