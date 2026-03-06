package controller;

import dao.AuditLogDAO;
import dao.CodReconciliationDAO;
import dao.InvoiceDAO;
import dao.PaymentDAO;
import model.CodReconciliation;
import model.Invoice;
import model.Payment;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * AccountingServlet — Servlet độc lập xử lý kế toán.
 * URL: /accounting?action=xxx
 *
 * doGet : hiển thị danh sách invoice, chi tiết invoice, danh sách payment, COD list
 * doPost: cập nhật invoice, lưu payment, cập nhật COD
 */
@WebServlet("/accounting")
public class AccountingServlet extends HttpServlet {

    private final InvoiceDAO          invoiceDAO = new InvoiceDAO();
    private final PaymentDAO          paymentDAO = new PaymentDAO();
    private final CodReconciliationDAO codDAO    = new CodReconciliationDAO();
    private final AuditLogDAO         auditDAO   = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "invoiceList":   showInvoiceList(req, resp);   break;
            case "invoiceDetail": showInvoiceDetail(req, resp); break;
            case "paymentList":   showPaymentList(req, resp);   break;
            case "codList":       showCodList(req, resp);       break;
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
            case "invoiceUpdate": handleInvoiceUpdate(req, resp); break;
            case "paymentSave":   handlePaymentSave(req, resp);   break;
            case "codUpdate":     handleCodUpdate(req, resp);     break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ INVOICE ══════════════

    private void showInvoiceList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("invoices", invoiceDAO.getAll());
        req.getRequestDispatcher("/views/invoice/invoiceList.jsp").forward(req, resp);
    }

    private void showInvoiceDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("invoice", invoiceDAO.getById(id));
        req.getRequestDispatcher("/views/invoice/invoiceDetail.jsp").forward(req, resp);
    }

    private void handleInvoiceUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Invoice inv = new Invoice();
        inv.setInvoiceId(Integer.parseInt(req.getParameter("invoiceId")));
        inv.setStatus(req.getParameter("status"));
        invoiceDAO.update(inv);
        logAction(req, "UPDATE", "Invoices", inv.getInvoiceId());
        resp.sendRedirect(req.getContextPath() + "/main?action=invoiceList");
    }

    // ══════════════ PAYMENT ══════════════

    private void showPaymentList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("payments", paymentDAO.getAll());
        req.setAttribute("invoices", invoiceDAO.getAll());
        req.getRequestDispatcher("/views/payment/paymentList.jsp").forward(req, resp);
    }

    private void handlePaymentSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Payment pay = new Payment();
        pay.setInvoiceId(Integer.parseInt(req.getParameter("invoiceId")));
        pay.setAmount(new BigDecimal(req.getParameter("amount")));
        pay.setMethod(req.getParameter("method"));
        paymentDAO.insert(pay);
        logAction(req, "INSERT", "Payments", 0);
        // Tự động đánh dấu hóa đơn Paid
        Invoice inv = invoiceDAO.getById(pay.getInvoiceId());
        if (inv != null) { inv.setStatus("Paid"); invoiceDAO.update(inv); }
        resp.sendRedirect(req.getContextPath() + "/main?action=paymentList");
    }

    // ══════════════ COD ══════════════

    private void showCodList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("cods", codDAO.getAll());
        req.getRequestDispatcher("/views/cod/codList.jsp").forward(req, resp);
    }

    private void handleCodUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        CodReconciliation cr = new CodReconciliation();
        cr.setCodId(Integer.parseInt(req.getParameter("codId")));
        cr.setReceivedCod(new BigDecimal(req.getParameter("receivedCod")));
        cr.setStatus(req.getParameter("status"));
        codDAO.update(cr);
        logAction(req, "UPDATE", "CodReconciliations", cr.getCodId());
        resp.sendRedirect(req.getContextPath() + "/main?action=codList");
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
