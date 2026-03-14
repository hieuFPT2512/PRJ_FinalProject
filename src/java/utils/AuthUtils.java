package utils;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthUtils — Tiện ích kiểm tra xác thực và phân quyền dùng chung.
 *
 * Cách dùng trong processRequest():
 *
 *   // 1. Kiểm tra đăng nhập
 *   User loginUser = AuthUtils.getLoginUser(request);
 *   if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }
 *
 *   // 2. Kiểm tra role (nếu cần)
 *   if (!AuthUtils.hasRole(loginUser, ROLE_ADMIN, ROLE_ACCOUNTANT)) {
 *       AuthUtils.denyAccess(request, response); return;
 *   }
 */
public class AuthUtils {

    /**
     * Lấy user đang đăng nhập từ session.
     * Trả về null nếu chưa đăng nhập.
     */
    public static User getLoginUser(HttpServletRequest request) {
        if (request.getSession(false) == null) return null;
        return (User) request.getSession(false).getAttribute("loggedUser");
    }

    /**
     * Kiểm tra user có thuộc ít nhất 1 trong các role được phép không.
     */
    public static boolean hasRole(User user, int... allowedRoles) {
        if (user == null) return false;
        for (int role : allowedRoles) {
            if (user.getRoleId() == role) return true;
        }
        return false;
    }

    /**
     * Redirect về trang login.
     */
    public static void redirectLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/main?action=login");
    }

    /**
     * Từ chối truy cập — forward đến trang 403.
     */
    public static void denyAccess(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        request.setAttribute("errorMessage", "You do not have permission to access this function.");
        request.getRequestDispatcher("/views/error/403.jsp").forward(request, response);
    }
}
