package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class ShowAllOrdersController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ShowAllOrdersController.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private OrderService orderService = (OrderService) INJECTOR.getInstance(OrderService.class);
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> allOrdersUser = orderService.getAll();
        User user = null;
        if (allOrdersUser.isEmpty()) {
            user = null;
            LOGGER.error("No orders in the database");
            req.setAttribute("massage", "You have no orders yet.");
        } else {
            try {
                user = userService.get(allOrdersUser.get(0).getUserId());
            } catch (DataProcessingException throwables) {
                throwables.printStackTrace();
            }
        }
        req.setAttribute("orders", allOrdersUser);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/orders/all.jsp").forward(req, resp);
    }
}
