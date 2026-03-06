package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) action = "dashboard";

        String target = resolveTarget(action);
        request.getRequestDispatcher(target).forward(request, response);
    }

    private String resolveTarget(String action) {
        switch (action) {
            case "dashboard": return "/views/dashboard.jsp";

            // ── Auth ──────────────────────────────────────────
            case "login": case "logout":
            case "userList": case "userForm": case "userSave": case "userDelete": case "auditLog":
                return "/auth";

            // ── Master Data ───────────────────────────────────
            case "customerList": case "customerForm": case "customerSave": case "customerDelete":
            case "productList":  case "productForm":  case "productSave":  case "productDelete":
            case "warehouseList":case "warehouseForm":case "warehouseSave":case "warehouseDelete":
                return "/masterdata";

            // ── Delivery ──────────────────────────────────────
            case "orderList":    case "orderForm":    case "orderDetail":
            case "orderSave":    case "orderDelete":
            case "shipmentList": case "shipmentForm": case "shipmentSave": case "shipmentUpdate":
            case "podList":      case "podUpload":    case "podConfirm":
                return "/delivery";

            // ── Warehouse ─────────────────────────────────────
            case "outboundList": case "outboundForm": case "outboundSave": case "outboundUpdate":
            case "inboundList":  case "inboundForm":  case "inboundSave":
            case "stockLedger":
                return "/warehouse";

            // ── Accounting ────────────────────────────────────
            case "invoiceList":  case "invoiceDetail": case "invoiceUpdate":
            case "paymentList":  case "paymentSave":
            case "codList":      case "codUpdate":
                return "/accounting";

            // ── Search ────────────────────────────────────────
            case "searchOrders": case "searchStockDocs": case "searchInvoices":
                return "/search";

            // ── Alert & Case ──────────────────────────────────
            case "alertList":  case "alertDetail": case "alertClose":
            case "caseList":   case "caseDetail":  case "caseAssign": case "caseClose":
                return "/alert";

            default: return "/views/dashboard.jsp";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
