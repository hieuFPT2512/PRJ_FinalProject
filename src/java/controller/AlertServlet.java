package controller;

import dao.AlertEventDAO;
import dao.AuditLogDAO;
import dao.ReconciliationCaseDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AlertServlet — Servlet độc lập xử lý cảnh báo và case đối soát.
 * URL: /alert?action=xxx
 *
 * doGet : hiển thị danh sách alert, chi tiết alert, đóng alert,
 *         danh sách case, chi tiết case, đóng case
 * doPost: giao case (caseAssign)
 */
@WebServlet("/alert")
public class AlertServlet extends HttpServlet {

    private final AlertEventDAO         alertDAO = new AlertEventDAO();
    private final ReconciliationCaseDAO caseDAO  = new ReconciliationCaseDAO();
    private final AuditLogDAO           auditDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "alertList":   showAlertList(req, resp);    break;
            case "alertDetail": showAlertDetail(req, resp);  break;
            case "alertClose":  handleAlertClose(req, resp); break;
            case "caseList":    showCaseList(req, resp);     break;
            case "caseDetail":  showCaseDetail(req, resp);   break;
            case "caseClose":   handleCaseClose(req, resp);  break;
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
            case "caseAssign": handleCaseAssign(req, resp); break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ ALERTS ══════════════

    private void showAlertList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("alerts", alertDAO.getOpen());
        req.getRequestDispatcher("/views/alert/alertsList.jsp").forward(req, resp);
    }

    private void showAlertDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("alert", alertDAO.getById(id));
        req.getRequestDispatcher("/views/alert/alertDetail.jsp").forward(req, resp);
    }

    private void handleAlertClose(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        alertDAO.updateStatus(id, "Closed");
        logAction(req, "CLOSE", "AlertEvents", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=alertList");
    }

    // ══════════════ CASES ══════════════

    private void showCaseList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("cases", caseDAO.getAll());
        req.getRequestDispatcher("/views/reconcase/caseList.jsp").forward(req, resp);
    }

    private void showCaseDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("recase", caseDAO.getById(id));
        req.getRequestDispatcher("/views/reconcase/caseDetail.jsp").forward(req, resp);
    }

    private void handleCaseAssign(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int caseId = Integer.parseInt(req.getParameter("caseId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        caseDAO.assignCase(caseId, userId);
        logAction(req, "UPDATE", "ReconciliationCases", caseId);
        resp.sendRedirect(req.getContextPath() + "/main?action=caseDetail&id=" + caseId);
    }

    private void handleCaseClose(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        caseDAO.updateStatus(id, "Closed");
        logAction(req, "CLOSE", "ReconciliationCases", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=caseList");
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
