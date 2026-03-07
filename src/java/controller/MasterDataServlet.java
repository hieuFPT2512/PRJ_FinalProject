package controller;

import dao.AuditLogDAO;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.WarehouseDAO;
import model.Customer;
import model.Product;
import model.User;
import model.Warehouse;
import utils.AuthUtils;
import utils.RoleConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Phân quyền MasterDataServlet:
 *   *List / *Form (xem)  → ADMIN, WAREHOUSE_STAFF, CUSTOMER_SERVICE
 *   *Save / *Delete      → ADMIN, WAREHOUSE_STAFF
 */
@WebServlet("/masterdata")
public class MasterDataServlet extends HttpServlet {

    private final CustomerDAO  customerDAO  = new CustomerDAO();
    private final ProductDAO   productDAO   = new ProductDAO();
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final AuditLogDAO  auditDAO     = new AuditLogDAO();

    // Role được phép XEM danh mục
    private static final int[] CAN_VIEW   = {
        RoleConstants.ROLE_ADMIN,
        RoleConstants.ROLE_WAREHOUSE_STAFF,
        RoleConstants.ROLE_CUSTOMER_SERVICE
    };
    // Role được phép SỬA / XÓA danh mục
    private static final int[] CAN_EDIT   = {
        RoleConstants.ROLE_ADMIN,
        RoleConstants.ROLE_WAREHOUSE_STAFF
    };

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        // Tất cả action đều cần ít nhất quyền XEM
        if (!AuthUtils.hasRole(loginUser, CAN_VIEW)) {
            AuthUtils.denyAccess(request, response); return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ CUSTOMER ══════════════
            case "customerList":
                String kw = request.getParameter("keyword");
                request.setAttribute("customers", (kw != null && !kw.isEmpty())
                        ? customerDAO.search(kw) : customerDAO.getAll());
                request.setAttribute("keyword", kw);
                request.getRequestDispatcher("/views/customer/customerList.jsp").forward(request, response);
                break;

            case "customerForm":
                String custId = request.getParameter("id");
                if (custId != null && !custId.isEmpty())
                    request.setAttribute("customer", customerDAO.getById(Integer.parseInt(custId)));
                request.setAttribute("canEdit", AuthUtils.hasRole(loginUser, CAN_EDIT));
                request.getRequestDispatcher("/views/customer/customerForm.jsp").forward(request, response);
                break;

            case "customerSave":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                Customer c = new Customer();
                String cId = request.getParameter("customerId");
                c.setCustomerName(request.getParameter("customerName"));
                c.setPhone(request.getParameter("phone"));
                c.setEmail(request.getParameter("email"));
                c.setAddress(request.getParameter("address"));
                if (cId != null && !cId.isEmpty()) {
                    c.setCustomerId(Integer.parseInt(cId));
                    customerDAO.update(c);
                    logAction(request, "UPDATE", "Customers", c.getCustomerId());
                } else {
                    customerDAO.insert(c);
                    logAction(request, "INSERT", "Customers", 0);
                }
                response.sendRedirect(request.getContextPath() + "/main?action=customerList");
                break;

            case "customerDelete":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                int cDelId = Integer.parseInt(request.getParameter("id"));
                customerDAO.delete(cDelId);
                logAction(request, "DELETE", "Customers", cDelId);
                response.sendRedirect(request.getContextPath() + "/main?action=customerList");
                break;

            // ══════════════ PRODUCT ══════════════
            case "productList":
                request.setAttribute("products", productDAO.getAll());
                request.getRequestDispatcher("/views/product/productList.jsp").forward(request, response);
                break;

            case "productForm":
                String prodId = request.getParameter("id");
                if (prodId != null && !prodId.isEmpty())
                    request.setAttribute("product", productDAO.getById(Integer.parseInt(prodId)));
                request.setAttribute("canEdit", AuthUtils.hasRole(loginUser, CAN_EDIT));
                request.getRequestDispatcher("/views/product/productForm.jsp").forward(request, response);
                break;

            case "productSave":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                Product p = new Product();
                String pId = request.getParameter("productId");
                p.setSku(request.getParameter("sku"));
                p.setProductName(request.getParameter("productName"));
                p.setCategory(request.getParameter("category"));
                p.setUnit(request.getParameter("unit"));
                p.setPrice(new BigDecimal(request.getParameter("price")));
                if (pId != null && !pId.isEmpty()) {
                    p.setProductId(Integer.parseInt(pId));
                    productDAO.update(p);
                    logAction(request, "UPDATE", "Products", p.getProductId());
                } else {
                    productDAO.insert(p);
                    logAction(request, "INSERT", "Products", 0);
                }
                response.sendRedirect(request.getContextPath() + "/main?action=productList");
                break;

            case "productDelete":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                int pDelId = Integer.parseInt(request.getParameter("id"));
                productDAO.delete(pDelId);
                logAction(request, "DELETE", "Products", pDelId);
                response.sendRedirect(request.getContextPath() + "/main?action=productList");
                break;

            // ══════════════ WAREHOUSE ══════════════
            case "warehouseList":
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.getRequestDispatcher("/views/warehouse/warehouseList.jsp").forward(request, response);
                break;

            case "warehouseForm":
                String whId = request.getParameter("id");
                if (whId != null && !whId.isEmpty())
                    request.setAttribute("warehouse", warehouseDAO.getById(Integer.parseInt(whId)));
                request.setAttribute("canEdit", AuthUtils.hasRole(loginUser, CAN_EDIT));
                request.getRequestDispatcher("/views/warehouse/warehouseForm.jsp").forward(request, response);
                break;

            case "warehouseSave":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                Warehouse w = new Warehouse();
                String wId = request.getParameter("warehouseId");
                w.setWarehouseName(request.getParameter("warehouseName"));
                w.setLocation(request.getParameter("location"));
                w.setManager(request.getParameter("manager"));
                if (wId != null && !wId.isEmpty()) {
                    w.setWarehouseId(Integer.parseInt(wId));
                    warehouseDAO.update(w);
                    logAction(request, "UPDATE", "Warehouses", w.getWarehouseId());
                } else {
                    warehouseDAO.insert(w);
                    logAction(request, "INSERT", "Warehouses", 0);
                }
                response.sendRedirect(request.getContextPath() + "/main?action=warehouseList");
                break;

            case "warehouseDelete":
                if (!AuthUtils.hasRole(loginUser, CAN_EDIT)) { AuthUtils.denyAccess(request, response); return; }
                int wDelId = Integer.parseInt(request.getParameter("id"));
                warehouseDAO.delete(wDelId);
                logAction(request, "DELETE", "Warehouses", wDelId);
                response.sendRedirect(request.getContextPath() + "/main?action=warehouseList");
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
