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

@WebServlet("/alert")
public class AlertServlet extends HttpServlet {

    private final AlertEventDAO         alertDAO = new AlertEventDAO();
    private final ReconciliationCaseDAO caseDAO  = new ReconciliationCaseDAO();
    private final AuditLogDAO           auditDAO = new AuditLogDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ ALERTS ══════════════
            case "alertList":
                request.setAttribute("alerts", alertDAO.getOpen());
                request.getRequestDispatcher("/views/alert/alertsList.jsp").forward(request, response);
                break;

            case "alertDetail":
                request.setAttribute("alert", alertDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/alert/alertDetail.jsp").forward(request, response);
                break;

            case "alertClose":
                int alertId = Integer.parseInt(request.getParameter("id"));
                alertDAO.updateStatus(alertId, "Closed");
                logAction(request, "CLOSE", "AlertEvents", alertId);
                response.sendRedirect(request.getContextPath() + "/main?action=alertList");
                break;

            // ══════════════ CASES ══════════════
            case "caseList":
                request.setAttribute("cases", caseDAO.getAll());
                request.getRequestDispatcher("/views/reconcase/caseList.jsp").forward(request, response);
                break;

            case "caseDetail":
                request.setAttribute("recase", caseDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/reconcase/caseDetail.jsp").forward(request, response);
                break;

            case "caseAssign":
                int caseId = Integer.parseInt(request.getParameter("caseId"));
                int userId = Integer.parseInt(request.getParameter("userId"));
                caseDAO.assignCase(caseId, userId);
                logAction(request, "UPDATE", "ReconciliationCases", caseId);
                response.sendRedirect(request.getContextPath() + "/main?action=caseDetail&id=" + caseId);
                break;

            case "caseClose":
                int closeCaseId = Integer.parseInt(request.getParameter("id"));
                caseDAO.updateStatus(closeCaseId, "Closed");
                logAction(request, "CLOSE", "ReconciliationCases", closeCaseId);
                response.sendRedirect(request.getContextPath() + "/main?action=caseList");
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
