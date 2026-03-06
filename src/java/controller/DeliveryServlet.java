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

@WebServlet("/delivery")
@MultipartConfig
public class DeliveryServlet extends HttpServlet {

    private final DeliveryOrderDAO   orderDAO     = new DeliveryOrderDAO();
    private final ShipmentDAO        shipmentDAO  = new ShipmentDAO();
    private final ProofOfDeliveryDAO podDAO       = new ProofOfDeliveryDAO();
    private final CustomerDAO        customerDAO  = new CustomerDAO();
    private final WarehouseDAO       warehouseDAO = new WarehouseDAO();
    private final ProductDAO         productDAO   = new ProductDAO();
    private final AuditLogDAO        auditDAO     = new AuditLogDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ ORDERS ══════════════
            case "orderList":
                request.setAttribute("orders", orderDAO.getAll());
                request.getRequestDispatcher("/views/order/orderList.jsp").forward(request, response);
                break;

            case "orderForm":
                String oFormId = request.getParameter("id");
                if (oFormId != null && !oFormId.isEmpty())
                    request.setAttribute("order", orderDAO.getById(Integer.parseInt(oFormId)));
                request.setAttribute("customers",  customerDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.setAttribute("products",   productDAO.getAll());
                request.getRequestDispatcher("/views/order/orderForm.jsp").forward(request, response);
                break;

            case "orderDetail":
                request.setAttribute("order", orderDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/order/orderDetail.jsp").forward(request, response);
                break;

            case "orderSave":
                DeliveryOrder o = new DeliveryOrder();
                String oSaveId = request.getParameter("orderId");
                o.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
                o.setWarehouseId(Integer.parseInt(request.getParameter("warehouseId")));
                o.setRoute(request.getParameter("route"));
                o.setHasCod(request.getParameter("hasCod"));
                o.setTotalAmount(new BigDecimal(request.getParameter("totalAmount")));
                o.setStatus(request.getParameter("status"));
                if (oSaveId != null && !oSaveId.isEmpty()) {
                    o.setOrderId(Integer.parseInt(oSaveId));
                    orderDAO.update(o);
                    logAction(request, "UPDATE", "DeliveryOrders", o.getOrderId());
                } else {
                    int newId = orderDAO.insert(o);
                    logAction(request, "INSERT", "DeliveryOrders", newId);
                }
                response.sendRedirect(request.getContextPath() + "/main?action=orderList");
                break;

            case "orderDelete":
                int oDelId = Integer.parseInt(request.getParameter("id"));
                orderDAO.delete(oDelId);
                logAction(request, "DELETE", "DeliveryOrders", oDelId);
                response.sendRedirect(request.getContextPath() + "/main?action=orderList");
                break;

            // ══════════════ SHIPMENTS ══════════════
            case "shipmentList":
                request.setAttribute("shipments", shipmentDAO.getAll());
                request.getRequestDispatcher("/views/shipment/shipmentList.jsp").forward(request, response);
                break;

            case "shipmentForm":
                request.setAttribute("orders", orderDAO.getAll());
                request.getRequestDispatcher("/views/shipment/shipmentForm.jsp").forward(request, response);
                break;

            case "shipmentSave":
                Shipment s = new Shipment();
                s.setOrderId(Integer.parseInt(request.getParameter("orderId")));
                s.setDriverName(request.getParameter("driverName"));
                s.setStatus("Shipping");
                shipmentDAO.insert(s);
                logAction(request, "INSERT", "Shipments", 0);
                response.sendRedirect(request.getContextPath() + "/main?action=shipmentList");
                break;

            case "shipmentUpdate":
                Shipment su = new Shipment();
                su.setShipmentId(Integer.parseInt(request.getParameter("shipmentId")));
                su.setDriverName(request.getParameter("driverName"));
                su.setStatus(request.getParameter("status"));
                shipmentDAO.update(su);
                logAction(request, "UPDATE", "Shipments", su.getShipmentId());
                response.sendRedirect(request.getContextPath() + "/main?action=shipmentList");
                break;

            // ══════════════ POD ══════════════
            case "podList":
                request.setAttribute("pods", podDAO.getAll());
                request.getRequestDispatcher("/views/pod/podList.jsp").forward(request, response);
                break;

            case "podUpload":
                if ("POST".equalsIgnoreCase(request.getMethod())) {
                    ProofOfDelivery pod = new ProofOfDelivery();
                    pod.setShipmentId(Integer.parseInt(request.getParameter("shipmentId")));
                    pod.setSignedBy(request.getParameter("signedBy"));
                    pod.setImageUrl(request.getParameter("imageUrl"));
                    pod.setStatus("Pending");
                    podDAO.insert(pod);
                    logAction(request, "INSERT", "ProofOfDeliveries", 0);
                    response.sendRedirect(request.getContextPath() + "/main?action=podList");
                } else {
                    request.setAttribute("shipments", shipmentDAO.getAll());
                    request.getRequestDispatcher("/views/pod/podUpload.jsp").forward(request, response);
                }
                break;

            case "podConfirm":
                ProofOfDelivery pc = new ProofOfDelivery();
                pc.setPodId(Integer.parseInt(request.getParameter("podId")));
                pc.setSignedBy(request.getParameter("signedBy"));
                pc.setStatus("Completed");
                podDAO.update(pc);
                logAction(request, "UPDATE", "ProofOfDeliveries", pc.getPodId());
                response.sendRedirect(request.getContextPath() + "/main?action=podList");
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
