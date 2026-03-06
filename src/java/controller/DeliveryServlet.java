package controller;

import dao.AuditLogDAO;
import dao.CustomerDAO;
import dao.DeliveryOrderDAO;
import dao.ProductDAO;
import dao.ProofOfDeliveryDAO;
import dao.ShipmentDAO;
import dao.WarehouseDAO;
import model.DeliveryOrder;
import model.ProofOfDelivery;
import model.Shipment;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * DeliveryServlet — Servlet độc lập xử lý vận chuyển.
 * URL: /delivery?action=xxx
 *
 * doGet : hiển thị danh sách, form, chi tiết, xử lý delete
 * doPost: lưu order, shipment, POD upload/confirm
 */
@WebServlet("/delivery")
@MultipartConfig
public class DeliveryServlet extends HttpServlet {

    private final DeliveryOrderDAO   orderDAO    = new DeliveryOrderDAO();
    private final ShipmentDAO        shipmentDAO = new ShipmentDAO();
    private final ProofOfDeliveryDAO podDAO      = new ProofOfDeliveryDAO();
    private final CustomerDAO        customerDAO = new CustomerDAO();
    private final WarehouseDAO       warehouseDAO= new WarehouseDAO();
    private final ProductDAO         productDAO  = new ProductDAO();
    private final AuditLogDAO        auditDAO    = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "orderList":    showOrderList(req, resp);    break;
            case "orderForm":    showOrderForm(req, resp);    break;
            case "orderDetail":  showOrderDetail(req, resp);  break;
            case "orderDelete":  handleOrderDelete(req, resp); break;
            case "shipmentList": showShipmentList(req, resp); break;
            case "shipmentForm": showShipmentForm(req, resp); break;
            case "podList":      showPodList(req, resp);      break;
            case "podUpload":    showPodUploadForm(req, resp);break;
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
            case "orderSave":      handleOrderSave(req, resp);      break;
            case "shipmentSave":   handleShipmentSave(req, resp);   break;
            case "shipmentUpdate": handleShipmentUpdate(req, resp); break;
            case "podUpload":      handlePodUpload(req, resp);      break;
            case "podConfirm":     handlePodConfirm(req, resp);     break;
            default: resp.sendRedirect(req.getContextPath() + "/main");
        }
    }

    // ══════════════ ORDERS ══════════════

    private void showOrderList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("orders", orderDAO.getAll());
        req.getRequestDispatcher("/views/order/orderList.jsp").forward(req, resp);
    }

    private void showOrderForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            req.setAttribute("order", orderDAO.getById(Integer.parseInt(id)));
        req.setAttribute("customers",  customerDAO.getAll());
        req.setAttribute("warehouses", warehouseDAO.getAll());
        req.setAttribute("products",   productDAO.getAll());
        req.getRequestDispatcher("/views/order/orderForm.jsp").forward(req, resp);
    }

    private void showOrderDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("order", orderDAO.getById(id));
        req.getRequestDispatcher("/views/order/orderDetail.jsp").forward(req, resp);
    }

    private void handleOrderSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        DeliveryOrder o = new DeliveryOrder();
        String id = req.getParameter("orderId");
        o.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
        o.setWarehouseId(Integer.parseInt(req.getParameter("warehouseId")));
        o.setRoute(req.getParameter("route"));
        o.setHasCod(req.getParameter("hasCod"));
        o.setTotalAmount(new BigDecimal(req.getParameter("totalAmount")));
        o.setStatus(req.getParameter("status"));
        if (id != null && !id.isEmpty()) {
            o.setOrderId(Integer.parseInt(id));
            orderDAO.update(o);
            logAction(req, "UPDATE", "DeliveryOrders", o.getOrderId());
        } else {
            int newId = orderDAO.insert(o);
            logAction(req, "INSERT", "DeliveryOrders", newId);
        }
        resp.sendRedirect(req.getContextPath() + "/main?action=orderList");
    }

    private void handleOrderDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        orderDAO.delete(id);
        logAction(req, "DELETE", "DeliveryOrders", id);
        resp.sendRedirect(req.getContextPath() + "/main?action=orderList");
    }

    // ══════════════ SHIPMENTS ══════════════

    private void showShipmentList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("shipments", shipmentDAO.getAll());
        req.getRequestDispatcher("/views/shipment/shipmentList.jsp").forward(req, resp);
    }

    private void showShipmentForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("orders", orderDAO.getAll());
        req.getRequestDispatcher("/views/shipment/shipmentForm.jsp").forward(req, resp);
    }

    private void handleShipmentSave(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Shipment s = new Shipment();
        s.setOrderId(Integer.parseInt(req.getParameter("orderId")));
        s.setDriverName(req.getParameter("driverName"));
        s.setStatus("Shipping");
        shipmentDAO.insert(s);
        logAction(req, "INSERT", "Shipments", 0);
        resp.sendRedirect(req.getContextPath() + "/main?action=shipmentList");
    }

    private void handleShipmentUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Shipment s = new Shipment();
        s.setShipmentId(Integer.parseInt(req.getParameter("shipmentId")));
        s.setDriverName(req.getParameter("driverName"));
        s.setStatus(req.getParameter("status"));
        shipmentDAO.update(s);
        logAction(req, "UPDATE", "Shipments", s.getShipmentId());
        resp.sendRedirect(req.getContextPath() + "/main?action=shipmentList");
    }

    // ══════════════ POD ══════════════

    private void showPodList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("pods", podDAO.getAll());
        req.getRequestDispatcher("/views/pod/podList.jsp").forward(req, resp);
    }

    private void showPodUploadForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("shipments", shipmentDAO.getAll());
        req.getRequestDispatcher("/views/pod/podUpload.jsp").forward(req, resp);
    }

    private void handlePodUpload(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ProofOfDelivery pod = new ProofOfDelivery();
        pod.setShipmentId(Integer.parseInt(req.getParameter("shipmentId")));
        pod.setSignedBy(req.getParameter("signedBy"));
        pod.setImageUrl(req.getParameter("imageUrl"));
        pod.setStatus("Pending");
        podDAO.insert(pod);
        logAction(req, "INSERT", "ProofOfDeliveries", 0);
        resp.sendRedirect(req.getContextPath() + "/main?action=podList");
    }

    private void handlePodConfirm(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ProofOfDelivery pod = new ProofOfDelivery();
        pod.setPodId(Integer.parseInt(req.getParameter("podId")));
        pod.setSignedBy(req.getParameter("signedBy"));
        pod.setStatus("Completed");
        podDAO.update(pod);
        logAction(req, "UPDATE", "ProofOfDeliveries", pod.getPodId());
        resp.sendRedirect(req.getContextPath() + "/main?action=podList");
    }

    // ── Helper ──────────────────────────────────
    private void logAction(HttpServletRequest req, String action, String table, int id) {
        User user = (User) req.getSession().getAttribute("loggedUser");
        if (user != null) auditDAO.log(user.getUserId(), action, table, id);
    }
}
