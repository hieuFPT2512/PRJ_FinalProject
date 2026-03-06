package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AuthFilter — Kiểm tra session trước mọi request.
 *
 * Public (không cần login):
 *   - /auth           → AuthServlet (login page, logout)
 *   - /css/, /js/, /images/  → static resources
 *
 * Protected (cần login):
 *   - /main, /delivery, /warehouse, /accounting, /search, /alert, /masterdata
 *   - /views/         → JSP files (forward nội bộ từ Servlet cũng qua filter)
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String action = req.getParameter("action");

        // ── Luôn cho qua: login và static resources ──────────────────────
        if (uri.equals(ctx + "/auth")
                || (uri.equals(ctx + "/main") && ("login".equals(action) || "logout".equals(action)))
                || uri.startsWith(ctx + "/css/")
                || uri.startsWith(ctx + "/js/")
                || uri.startsWith(ctx + "/images/")) {
            chain.doFilter(request, response);
            return;
        }

        // ── Kiểm tra session ─────────────────────────────────────────────
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (user != null) {
            chain.doFilter(request, response);
        } else {
            // Chưa đăng nhập → về login
            resp.sendRedirect(ctx + "/auth?action=login");
        }
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}
