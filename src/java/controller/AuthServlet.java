package controller;

import dao.AuditLogDAO;
import dao.RoleDAO;
import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthServlet — Servlet độc lập xử lý xác thực và quản lý người dùng.
 * URL: /auth?action=xxx
 *
 * doGet : login (hiển thị form), logout, userList, userForm, userDelete, auditLog
 * doPost: login (xử lý submit), userSave
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private final UserDAO     userDAO  = new UserDAO();
    private final RoleDAO     roleDAO  = new RoleDAO();
    private final AuditLogDAO auditDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "login";

        switch (action) {
            case "login":      showLoginForm(req, resp);    break;
            case "logout":     handleLogout(req, resp);     break;
            case "userList":   showUserList(req, resp);     break;
            case "userForm":   showUserForm(req, resp);     break;
            case "userDelete": handleUserDelete(req, resp); break;
            case "auditLog":   showAuditLog(req, resp);     break;
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
            case "login":    handleLogin(req, resp);    break;
            case "userSave": handleUserSave(req, resp); break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ GET handlers ══════════════

    private void showLoginForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/main?action=login");
    }

    private void showUserList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("users", userDAO.getAll());
        req.getRequestDispatcher("/views/auth/userList.jsp").forward(req, resp);
    }

    private void showUserForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            req.setAttribute("user", userDAO.getById(Integer.parseInt(id)));
        req.setAttribute("roles", roleDAO.getAll());
        req.getRequestDispatcher("/views/auth/userForm.jsp").forward(req, resp);
    }

    private void handleUserDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.delete(id);
        logAction(req, "DELETE", "Users", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=userList");
    }

    private void showAuditLog(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("logs", auditDAO.getAll());
        req.getRequestDispatcher("/views/auth/auditLog.jsp").forward(req, resp);
    }

    // ══════════════ POST handlers ══════════════

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userDAO.checkLogin(username, password);
        if (user != null) {
            req.getSession().setAttribute("loggedUser", user);
            resp.sendRedirect(req.getContextPath() + "/main?action=dashboard");
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleUserSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User u = new User();
        String id = req.getParameter("userId");
        u.setFullName(req.getParameter("fullName"));
        u.setRoleId(Integer.parseInt(req.getParameter("roleId")));
        u.setStatus(req.getParameter("status"));
        if (id != null && !id.isEmpty()) {
            u.setUserId(Integer.parseInt(id));
            userDAO.update(u);
            logAction(req, "UPDATE", "Users", u.getUserId());
        } else {
            u.setUsername(req.getParameter("username"));
            u.setPasswordHash(req.getParameter("password"));
            userDAO.insert(u);
            logAction(req, "INSERT", "Users", 0);
        }
        resp.sendRedirect(req.getContextPath() + "/main?action=userList");
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
