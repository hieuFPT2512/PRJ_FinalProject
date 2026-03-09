package controller;

import dao.AuditLogDAO;
import dao.CodReconciliationDAO;
import dao.InvoiceDAO;
import dao.PaymentDAO;
import model.CodReconciliation;
import model.Invoice;
import model.Payment;
import model.User;
import utils.AuthUtils;
import utils.RoleConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Phân quyền AccountingServlet:
 *   invoiceList / invoiceDetail / paymentList / codList (xem) → ADMIN, ACCOUNTANT
 *   invoiceUpdate / paymentSave / codUpdate (thao tác)        → ADMIN, ACCOUNTANT
 *   (Thủ kho, Tài xế, CSKH không được vào kế toán)
 */
@WebServlet("/accounting")
public class AccountingServlet extends HttpServlet {

    private final InvoiceDAO           invoiceDAO = new InvoiceDAO();
    private final PaymentDAO           paymentDAO = new PaymentDAO();
    private final CodReconciliationDAO codDAO     = new CodReconciliationDAO();
    private final AuditLogDAO          auditDAO   = new AuditLogDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        // Toàn bộ kế toán: chỉ ADMIN và ACCOUNTANT
        if (!AuthUtils.hasRole(loginUser,
                RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_ACCOUNTANT)) {
            AuthUtils.denyAccess(request, response); return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ HÓA ĐƠN ══════════════
            case "invoiceList":
                request.setAttribute("invoices", invoiceDAO.getAll());
                request.getRequestDispatcher("/views/invoice/invoiceList.jsp").forward(request, response);
                break;

            case "invoiceDetail":
                request.setAttribute("invoice", invoiceDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/invoice/invoiceDetail.jsp").forward(request, response);
                break;

            case "invoiceUpdate":
                Invoice inv = new Invoice();
                inv.setInvoiceId(Integer.parseInt(request.getParameter("invoiceId")));
                inv.setStatus(request.getParameter("status"));
                invoiceDAO.update(inv);
                logAction(request, "UPDATE", "Invoices", inv.getInvoiceId());
                response.sendRedirect(request.getContextPath() + "/main?action=invoiceList");
                break;

            // ══════════════ THANH TOÁN ══════════════
            case "paymentList":
                request.setAttribute("payments", paymentDAO.getAll());
                request.setAttribute("invoices", invoiceDAO.getAll());
                request.getRequestDispatcher("/views/payment/paymentList.jsp").forward(request, response);
                break;

            case "paymentSave":
                Payment pay = new Payment();
                pay.setInvoiceId(Integer.parseInt(request.getParameter("invoiceId")));
                pay.setAmount(new BigDecimal(request.getParameter("amount")));
                pay.setMethod(request.getParameter("method"));
                paymentDAO.insert(pay);
                logAction(request, "INSERT", "Payments", 0);
                // Tự động đánh dấu hóa đơn Paid
                Invoice paidInv = invoiceDAO.getById(pay.getInvoiceId());
                if (paidInv != null) { paidInv.setStatus("Paid"); invoiceDAO.update(paidInv); }
                response.sendRedirect(request.getContextPath() + "/main?action=paymentList");
                break;

            // ══════════════ ĐỐI SOÁT COD ══════════════
            case "codList":
                request.setAttribute("cods", codDAO.getAll());
                request.getRequestDispatcher("/views/cod/codList.jsp").forward(request, response);
                break;

            case "codUpdate":
                CodReconciliation cr = new CodReconciliation();
                cr.setCodId(Integer.parseInt(request.getParameter("codId")));
                cr.setReceivedCod(new BigDecimal(request.getParameter("receivedCod")));
                cr.setStatus(request.getParameter("status"));
                codDAO.update(cr);
                logAction(request, "UPDATE", "CodReconciliations", cr.getCodId());
                response.sendRedirect(request.getContextPath() + "/main?action=codList");
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
