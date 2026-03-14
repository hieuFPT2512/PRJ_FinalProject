package controller;

import dao.AuditLogDAO;
import dao.RoleDAO;
import dao.UserDAO;
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
 * Phân quyền:
 *   login / logout     → tất cả (public)
 *   userList / userForm / userSave / userDelete / auditLog → CHỈ ADMIN (1)
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private final UserDAO     userDAO  = new UserDAO();
    private final RoleDAO     roleDAO  = new RoleDAO();
    private final AuditLogDAO auditDAO = new AuditLogDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "login";

        // ── Public: không cần đăng nhập ──────────────────────────────────
        if ("login".equals(action)) {
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                User user = userDAO.checkLogin(username, password);
                if (user != null) {
                    request.getSession().setAttribute("loggedUser", user);
                    response.sendRedirect(request.getContextPath() + "/main?action=dashboard");
                } else {
                    request.setAttribute("error", "Incorrect username or password!");
                    request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            }
            return;
        }

        if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/main?action=login");
            return;
        }

        // ── Phải đăng nhập ───────────────────────────────────────────────
        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        // ── Chỉ ADMIN ────────────────────────────────────────────────────
        if (!AuthUtils.hasRole(loginUser, RoleConstants.ROLE_ADMIN)) {
            AuthUtils.denyAccess(request, response); return;
        }

        switch (action) {
            case "userList":
                request.setAttribute("users", userDAO.getAll());
                request.getRequestDispatcher("/views/auth/userList.jsp").forward(request, response);
                break;

            case "userForm":
                String formId = request.getParameter("id");
                if (formId != null && !formId.isEmpty())
                    request.setAttribute("user", userDAO.getById(Integer.parseInt(formId)));
                request.setAttribute("roles", roleDAO.getAll());
                request.getRequestDispatcher("/views/auth/userForm.jsp").forward(request, response);
                break;

            case "userSave":
                User u = new User();
                String saveId = request.getParameter("userId");
                u.setFullName(request.getParameter("fullName"));
                u.setRoleId(Integer.parseInt(request.getParameter("roleId")));
                u.setStatus(request.getParameter("status"));
                if (saveId != null && !saveId.isEmpty()) {
                    u.setUserId(Integer.parseInt(saveId));
                    userDAO.update(u);
                    logAction(request, "UPDATE", "Users", u.getUserId());
                } else {
                    u.setUsername(request.getParameter("username"));
                    u.setPasswordHash(request.getParameter("password"));
                    userDAO.insert(u);
                    logAction(request, "INSERT", "Users", 0);
                }
                response.sendRedirect(request.getContextPath() + "/main?action=userList");
                break;

            case "userDelete":
                int delId = Integer.parseInt(request.getParameter("id"));
                userDAO.delete(delId);
                logAction(request, "DELETE", "Users", delId);
                response.sendRedirect(request.getContextPath() + "/main?action=userList");
                break;

            case "auditLog":
                request.setAttribute("logs", auditDAO.getAll());
                request.getRequestDispatcher("/views/auth/auditLog.jsp").forward(request, response);
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
