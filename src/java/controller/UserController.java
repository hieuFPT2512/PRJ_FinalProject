package controller;

import authentication.User;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {

    private static final String USER_LIST_PAGE = "admin/userList.jsp";
    private static final String USER_FORM_PAGE = "admin/userForm.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = USER_LIST_PAGE;
        try {
            String action = request.getParameter("action");
            UserDAO dao = new UserDAO();
            if (action == null) {
                action = "UserList";
            }

            switch (action) {
                case "UserList":
                    List<User> list = dao.getAllUsers();
                    request.setAttribute("userList", list);
                    url = USER_LIST_PAGE;
                    break;
                case "AddUserForm":
                    url = USER_FORM_PAGE;
                    break;
                case "AddUser":
                    User uNew = new User();
                    uNew.setUsername(request.getParameter("username"));
                    uNew.setPasswordHash(request.getParameter("password"));
                    uNew.setFullName(request.getParameter("fullName"));
                    uNew.setRoleId(Integer.parseInt(request.getParameter("roleId")));
                    
                    boolean checkInsert = dao.insertUser(uNew);
                    if (checkInsert) {
                        request.setAttribute("message", "Thêm mới thành công!");
                        request.setAttribute("alertType", "success");

                        List<User> listAfterInsert = dao.getAllUsers();
                        request.setAttribute("userList", listAfterInsert);
                        url = USER_LIST_PAGE;
                    } else {
                        request.setAttribute("message", "Thêm mới thất bại! Tên đăng nhập có thể đã tồn tại.");
                        request.setAttribute("alertType", "error");

                        request.setAttribute("user", uNew); // Giữ lại dữ liệu đã nhập
                        url = USER_FORM_PAGE;
                    }
                    break;
                case "UpdateUserForm":
                    int idUpdateForm = Integer.parseInt(request.getParameter("id"));
                    User user = dao.getUserById(idUpdateForm);
                    request.setAttribute("user", user);
                    url = USER_FORM_PAGE;
                    break;
                case "UpdateUser":
                    int idUpdate = Integer.parseInt(request.getParameter("id"));
                    User oldUser = dao.getUserById(idUpdate);
                    String newPass = request.getParameter("password");
                    String passToSave = oldUser.getPasswordHash();
                    if (newPass != null && !newPass.trim().isEmpty()) {
                        passToSave = newPass;
                    }
                    User uUpdate = new User();
                    uUpdate.setUserId(idUpdate);
                    uUpdate.setFullName(request.getParameter("fullName"));
                    uUpdate.setRoleId(Integer.parseInt(request.getParameter("roleId")));
                    uUpdate.setPasswordHash(passToSave);
                    uUpdate.setStatus(request.getParameter("status"));

                    boolean checkUpdate = dao.updateUser(uUpdate);
                    if (checkUpdate) {
                        request.setAttribute("message", "Cập nhật thành công!");
                        request.setAttribute("alertType", "success");

                        List<User> listAfterUpdate = dao.getAllUsers();
                        request.setAttribute("userList", listAfterUpdate);
                        url = USER_LIST_PAGE;
                    } else {
                        request.setAttribute("message", "Cập nhật thất bại! Vui lòng thử lại.");
                        request.setAttribute("alertType", "error");

                        request.setAttribute("user", uUpdate);
                        url = USER_FORM_PAGE;
                    }
                    break;
                case "DeleteUser":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    dao.deleteUser(idDelete);
                    List<User> listAfterDelete = dao.getAllUsers();
                    request.setAttribute("userList", listAfterDelete);
                    url = USER_LIST_PAGE;
                    break;
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
