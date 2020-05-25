package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.Order;
import dev.internetshop.model.User;
import dev.internetshop.service.OrderService;
import dev.internetshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ShowAllOrdersController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ShowAllOrdersController.class);
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> allOrdersUser = orderService.getAll();
        User user = null;
        if (allOrdersUser.isEmpty()) {
            user = null;
            LOGGER.error("No orders in the database");
            req.setAttribute("message", "You have no orders yet.");
        } else {
            try {
                user = userService.get(allOrdersUser.get(0).getUserId());
            } catch (DataProcessingException e) {
                new DataProcessingException("Can't receive list of orders",e);
            }
        }
        req.setAttribute("orders", allOrdersUser);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/orders/list.jsp").forward(req, resp);
    }
}
