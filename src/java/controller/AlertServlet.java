package controller;

import dao.AlertEventDAO;
import dao.AuditLogDAO;
import dao.ReconciliationCaseDAO;
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
 * Phân quyền AlertServlet:
 *   alertList / alertDetail / caseList / caseDetail (xem) → TẤT CẢ role đã đăng nhập
 *   alertClose                                             → ADMIN, ACCOUNTANT, WAREHOUSE_STAFF
 *   caseAssign / caseClose                                 → ADMIN, ACCOUNTANT
 */
@WebServlet("/alert")
public class AlertServlet extends HttpServlet {

    private final AlertEventDAO         alertDAO = new AlertEventDAO();
    private final ReconciliationCaseDAO caseDAO  = new ReconciliationCaseDAO();
    private final AuditLogDAO           auditDAO = new AuditLogDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ CẢNH BÁO ══════════════

            // Xem danh sách và chi tiết: tất cả role
            case "alertList":
                request.setAttribute("alerts", alertDAO.getOpen());
                request.getRequestDispatcher("/views/alert/alertsList.jsp").forward(request, response);
                break;

            case "alertDetail":
                request.setAttribute("alert", alertDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/alert/alertDetail.jsp").forward(request, response);
                break;

            // Đóng alert: ADMIN, Kế toán, Thủ kho
            case "alertClose":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_ACCOUNTANT,
                        RoleConstants.ROLE_WAREHOUSE_STAFF)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                int alertId = Integer.parseInt(request.getParameter("id"));
                alertDAO.updateStatus(alertId, "Closed");
                logAction(request, "CLOSE", "AlertEvents", alertId);
                response.sendRedirect(request.getContextPath() + "/main?action=alertList");
                break;

            // ══════════════ CASE ĐỐI SOÁT ══════════════

            // Xem danh sách và chi tiết case: tất cả role
            case "caseList":
                request.setAttribute("cases", caseDAO.getAll());
                request.getRequestDispatcher("/views/reconcase/caseList.jsp").forward(request, response);
                break;

            case "caseDetail":
                request.setAttribute("recase", caseDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/reconcase/caseDetail.jsp").forward(request, response);
                break;

            // Giao case và đóng case: chỉ ADMIN và Kế toán
            case "caseAssign":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_ACCOUNTANT)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                int caseId = Integer.parseInt(request.getParameter("caseId"));
                int userId = Integer.parseInt(request.getParameter("userId"));
                caseDAO.assignCase(caseId, userId);
                logAction(request, "UPDATE", "ReconciliationCases", caseId);
                response.sendRedirect(request.getContextPath() + "/main?action=caseDetail&id=" + caseId);
                break;

            case "caseClose":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_ACCOUNTANT)) {
                    AuthUtils.denyAccess(request, response); return;
                }
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
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}
