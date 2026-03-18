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
import utils.AuthUtils;
import utils.RoleConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Phân quyền DeliveryServlet:
 *
 *   Đơn giao hàng (order):
 *     orderList / orderDetail → ADMIN, WAREHOUSE_STAFF, DRIVER, CUSTOMER_SERVICE
 *     orderForm / orderSave / orderDelete → ADMIN, WAREHOUSE_STAFF, CUSTOMER_SERVICE
 *
 *   Lô hàng (shipment):
 *     shipmentList → ADMIN, WAREHOUSE_STAFF, DRIVER, CUSTOMER_SERVICE
 *     shipmentForm / shipmentSave → ADMIN, WAREHOUSE_STAFF
 *     shipmentUpdate (cập nhật trạng thái) → ADMIN, WAREHOUSE_STAFF, DRIVER
 *
 *   Bằng chứng giao hàng (POD):
 *     podList → ADMIN, WAREHOUSE_STAFF, DRIVER
 *     podUpload (GET form) → ADMIN, WAREHOUSE_STAFF, DRIVER
 *     podUpload (POST lưu) → ADMIN, WAREHOUSE_STAFF, DRIVER
 *     podConfirm (xác nhận) → ADMIN, WAREHOUSE_STAFF
 */
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

        User loginUser = AuthUtils.getLoginUser(request);
        if (loginUser == null) { AuthUtils.redirectLogin(request, response); return; }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            // ══════════════ ĐƠN GIAO HÀNG ══════════════

            case "orderList":
                // ADMIN, WAREHOUSE_STAFF, DRIVER (chỉ xem), CUSTOMER_SERVICE
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                request.setAttribute("orders", orderDAO.getAll());
                request.getRequestDispatcher("/views/order/orderList.jsp").forward(request, response);
                break;

            case "orderForm":
                // ADMIN, WAREHOUSE_STAFF, CUSTOMER_SERVICE được tạo/sửa
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                String oFormId = request.getParameter("id");
                if (oFormId != null && !oFormId.isEmpty())
                    request.setAttribute("order", orderDAO.getById(Integer.parseInt(oFormId)));
                request.setAttribute("customers",  customerDAO.getAll());
                request.setAttribute("warehouses", warehouseDAO.getAll());
                request.setAttribute("products",   productDAO.getAll());
                request.getRequestDispatcher("/views/order/orderForm.jsp").forward(request, response);
                break;

            case "orderDetail":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                request.setAttribute("order", orderDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/views/order/orderDetail.jsp").forward(request, response);
                break;

            case "orderSave":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
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

            case "orderCancel":
                // Only ADMIN can cancel orders
                if (!AuthUtils.hasRole(loginUser, RoleConstants.ROLE_ADMIN)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                int oCancelId = Integer.parseInt(request.getParameter("id"));
                orderDAO.updateStatus(oCancelId, "Cancelled");
                logAction(request, "UPDATE", "DeliveryOrders", oCancelId);
                request.setAttribute("success", "Order #" + oCancelId + " has been cancelled.");
                request.setAttribute("orders", orderDAO.getAll());
                request.getRequestDispatcher("/views/order/orderList.jsp").forward(request, response);
                break;

            // ══════════════ LÔ HÀNG ══════════════

            case "shipmentList":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER,
                        RoleConstants.ROLE_CUSTOMER_SERVICE)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                request.setAttribute("shipments", shipmentDAO.getAll());
                request.getRequestDispatcher("/views/shipment/shipmentList.jsp").forward(request, response);
                break;

            case "shipmentForm":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                request.setAttribute("orders", orderDAO.getAll());
                request.getRequestDispatcher("/views/shipment/shipmentForm.jsp").forward(request, response);
                break;

            case "shipmentSave":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                Shipment s = new Shipment();
                s.setOrderId(Integer.parseInt(request.getParameter("orderId")));
                s.setDriverName(request.getParameter("driverName"));
                s.setStatus("Shipping");
                shipmentDAO.insert(s);
                logAction(request, "INSERT", "Shipments", 0);
                response.sendRedirect(request.getContextPath() + "/main?action=shipmentList");
                break;

            case "shipmentUpdate":
                // Tài xế cũng được cập nhật trạng thái lô hàng của mình
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                Shipment su = new Shipment();
                su.setShipmentId(Integer.parseInt(request.getParameter("shipmentId")));
                su.setDriverName(request.getParameter("driverName"));
                su.setStatus(request.getParameter("status"));
                if ("Delivered".equals(request.getParameter("status"))) {
                    su.setDeliveryDate(new java.sql.Timestamp(System.currentTimeMillis()));
                } else {
                    su.setDeliveryDate(null);
                }
                shipmentDAO.update(su);
                logAction(request, "UPDATE", "Shipments", su.getShipmentId());
                response.sendRedirect(request.getContextPath() + "/main?action=shipmentList");
                break;

            // ══════════════ BẰNG CHỨNG GIAO HÀNG ══════════════

            case "podList":
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                request.setAttribute("pods", podDAO.getAll());
                request.getRequestDispatcher("/views/pod/podList.jsp").forward(request, response);
                break;

            case "podUpload":
                // Tài xế upload POD của mình
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF,
                        RoleConstants.ROLE_DRIVER)) {
                    AuthUtils.denyAccess(request, response); return;
                }
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
                // Chỉ ADMIN và Thủ kho xác nhận POD
                if (!AuthUtils.hasRole(loginUser,
                        RoleConstants.ROLE_ADMIN,
                        RoleConstants.ROLE_WAREHOUSE_STAFF)) {
                    AuthUtils.denyAccess(request, response); return;
                }
                ProofOfDelivery pc = new ProofOfDelivery();
                pc.setPodId(Integer.parseInt(request.getParameter("podId")));
                pc.setSignedBy(request.getParameter("signedBy"));
                pc.setStatus("Valid");
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
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}
