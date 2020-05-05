package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class ShowUserOrdersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        List<Order> userOrders = orderService.getUserOrders(userService.get(userId));
        req.setAttribute("orders", userOrders);
        req.setAttribute("user", userService.get(userId));
        req.getRequestDispatcher("/WEB-INF/views/orders/ShowAllOrdersByOneUser.jsp")
                .forward(req, resp);
    }
}
