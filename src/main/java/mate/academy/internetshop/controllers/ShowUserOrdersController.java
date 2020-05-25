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
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class ShowUserOrdersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        List<Order> userOrders = null;
        try {
            userOrders = orderService.getUserOrders(userService.get(userId));
        } catch (DataProcessingException e) {
           throw new DataProcessingException("Can't receive list of orders by one user",e);
        }
        req.setAttribute("orders", userOrders);
        req.setAttribute("user", userService.get(userId));
        req.getRequestDispatcher("/WEB-INF/views/orders/ShowAllOrdersByOneUser.jsp")
                .forward(req, resp);
    }
}
