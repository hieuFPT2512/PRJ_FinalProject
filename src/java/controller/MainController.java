package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController extends HttpServlet {
    
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String USER = "User";
    private static final String USER_CONTROLLER = "UserController";
    private static final String ROLE_LIST = "RoleList";
    private static final String ROLE_LIST_CONTROLLER = "RoleListController";
    private static final String CUSTOMER_LIST = "CustomerList";
    private static final String CUSTOMER_LIST_CONTROLLER = "CustomerListController";
    private static final String AUDIT_LOG = "AuditLog";
    private static final String AUDIT_LOG_CONTROLLER = "AuditLogController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "login.jsp";
        try {
            String action = request.getParameter("action");
            if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if("UserList".equals(action) || "AddUserForm".equals(action) || "AddUser".equals(action)
                    || "UpdateUserForm".equals(action) || "UpdateUser".equals(action) || "DeleteUser".equals(action)){
                url = USER_CONTROLLER;
            } else if (ROLE_LIST.equals(action)){
                url = ROLE_LIST_CONTROLLER;
            } else if (CUSTOMER_LIST.equals(action)){
                url = CUSTOMER_LIST_CONTROLLER;
            } else if (AUDIT_LOG.equals(action)){
                url = AUDIT_LOG_CONTROLLER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
