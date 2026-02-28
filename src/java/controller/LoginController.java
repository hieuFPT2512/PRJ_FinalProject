package controller;

import authentication.User;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.checkLogin(userName, password);

        if (user == null) {
            request.setAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            if ("Inactive".equals(user.getStatus())) {
                request.setAttribute("errorMessage", "Tài khoản của bạn đang bị khóa (Inactive). Vui lòng liên hệ Admin!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("USER_INFO", user);

                String role = user.getRoleName();
                if ("Admin".equalsIgnoreCase(role)) {
                    // Admin vào trang quản lý người dùng
                    response.sendRedirect("admin/dashboard.jsp");
                } else if ("Accountant".equalsIgnoreCase(role)) {
                    // Kế toán vào trang bàn làm việc đối soát [cite: 100]
                    response.sendRedirect("accounting/dashboard.jsp");
                } else if ("Warehouse Staff".equalsIgnoreCase(role)) {
                    // Thủ kho vào trang quản lý xuất kho [cite: 91]
                    response.sendRedirect("warehouse/dashboard.jsp");
                } else if ("Driver".equalsIgnoreCase(role)) {
                    // Tài xế vào xem danh sách chuyến giao của mình
                    response.sendRedirect("driver/dashboard.jsp");
                } else if ("Customer Service".equalsIgnoreCase(role)) {
                    // CSKH vào trang danh sách đơn hàng để hỗ trợ khách [cite: 87]
                    response.sendRedirect("orders/dashboard.jsp");
                } else {
                    // Role lạ hoặc chưa phân quyền thì về trang chung
                    response.sendRedirect("home.jsp");
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
