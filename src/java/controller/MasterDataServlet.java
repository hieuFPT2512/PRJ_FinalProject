package controller;

import dao.AuditLogDAO;
import dao.CustomerDAO;
import dao.ProductDAO;
import dao.WarehouseDAO;
import model.Customer;
import model.Product;
import model.User;
import model.Warehouse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * MasterDataServlet — Servlet độc lập xử lý CRUD danh mục.
 * URL: /masterdata?action=xxx
 *
 * doGet : hiển thị danh sách, form, xử lý delete
 * doPost: lưu (insert/update) customer, product, warehouse
 */
@WebServlet("/masterdata")
public class MasterDataServlet extends HttpServlet {

    private final CustomerDAO  customerDAO  = new CustomerDAO();
    private final ProductDAO   productDAO   = new ProductDAO();
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final AuditLogDAO  auditDAO     = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "customerList":     showCustomerList(req, resp);     break;
            case "customerForm":     showCustomerForm(req, resp);     break;
            case "customerDelete":   handleCustomerDelete(req, resp); break;
            case "productList":      showProductList(req, resp);      break;
            case "productForm":      showProductForm(req, resp);      break;
            case "productDelete":    handleProductDelete(req, resp);  break;
            case "warehouseList":    showWarehouseList(req, resp);    break;
            case "warehouseForm":    showWarehouseForm(req, resp);    break;
            case "warehouseDelete":  handleWarehouseDelete(req, resp);break;
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
            case "customerSave":  handleCustomerSave(req, resp);  break;
            case "productSave":   handleProductSave(req, resp);   break;
            case "warehouseSave": handleWarehouseSave(req, resp); break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ CUSTOMER ══════════════

    private void showCustomerList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String kw = req.getParameter("keyword");
        req.setAttribute("customers", (kw != null && !kw.isEmpty())
                ? customerDAO.search(kw) : customerDAO.getAll());
        req.setAttribute("keyword", kw);
        req.getRequestDispatcher("/views/customer/customerList.jsp").forward(req, resp);
    }

    private void showCustomerForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            req.setAttribute("customer", customerDAO.getById(Integer.parseInt(id)));
        req.getRequestDispatcher("/views/customer/customerForm.jsp").forward(req, resp);
    }

    private void handleCustomerSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Customer c = new Customer();
        String id = req.getParameter("customerId");
        c.setCustomerName(req.getParameter("customerName"));
        c.setPhone(req.getParameter("phone"));
        c.setEmail(req.getParameter("email"));
        c.setAddress(req.getParameter("address"));
        if (id != null && !id.isEmpty()) {
            c.setCustomerId(Integer.parseInt(id));
            customerDAO.update(c);
            logAction(req, "UPDATE", "Customers", c.getCustomerId());
        } else {
            customerDAO.insert(c);
            logAction(req, "INSERT", "Customers", 0);
        }
        resp.sendRedirect(req.getContextPath() + "/main?action=customerList");
    }

    private void handleCustomerDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        customerDAO.delete(id);
        logAction(req, "DELETE", "Customers", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=customerList");
    }

    // ══════════════ PRODUCT ══════════════

    private void showProductList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", productDAO.getAll());
        req.getRequestDispatcher("/views/product/productList.jsp").forward(req, resp);
    }

    private void showProductForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            req.setAttribute("product", productDAO.getById(Integer.parseInt(id)));
        req.getRequestDispatcher("/views/product/productForm.jsp").forward(req, resp);
    }

    private void handleProductSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Product p = new Product();
        String id = req.getParameter("productId");
        p.setSku(req.getParameter("sku"));
        p.setProductName(req.getParameter("productName"));
        p.setCategory(req.getParameter("category"));
        p.setUnit(req.getParameter("unit"));
        p.setPrice(new BigDecimal(req.getParameter("price")));
        if (id != null && !id.isEmpty()) {
            p.setProductId(Integer.parseInt(id));
            productDAO.update(p);
            logAction(req, "UPDATE", "Products", p.getProductId());
        } else {
            productDAO.insert(p);
            logAction(req, "INSERT", "Products", 0);
        }
        resp.sendRedirect(req.getContextPath() + "/main?action=productList");
    }

    private void handleProductDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productDAO.delete(id);
        logAction(req, "DELETE", "Products", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=productList");
    }

    // ══════════════ WAREHOUSE ══════════════

    private void showWarehouseList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.getRequestDispatcher("/views/warehouse/warehouseList.jsp").forward(req, resp);
    }

    private void showWarehouseForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            req.setAttribute("warehouse", warehouseDAO.getById(Integer.parseInt(id)));
        req.getRequestDispatcher("/views/warehouse/warehouseForm.jsp").forward(req, resp);
    }

    private void handleWarehouseSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Warehouse w = new Warehouse();
        String id = req.getParameter("warehouseId");
        w.setWarehouseName(req.getParameter("warehouseName"));
        w.setLocation(req.getParameter("location"));
        w.setManager(req.getParameter("manager"));
        if (id != null && !id.isEmpty()) {
            w.setWarehouseId(Integer.parseInt(id));
            warehouseDAO.update(w);
            logAction(req, "UPDATE", "Warehouses", w.getWarehouseId());
        } else {
            warehouseDAO.insert(w);
            logAction(req, "INSERT", "Warehouses", 0);
        }
        resp.sendRedirect(req.getContextPath() + "/main?action=warehouseList");
    }

    private void handleWarehouseDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        warehouseDAO.delete(id);
        logAction(req, "DELETE", "Warehouses", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=warehouseList");
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
