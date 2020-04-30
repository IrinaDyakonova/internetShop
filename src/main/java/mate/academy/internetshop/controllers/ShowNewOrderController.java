package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;

public class ShowNewOrderController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        Order order = orderService.get(id);
        req.getRequestDispatcher("/WEB-INF/views/orders/new.jsp")
                .forward(req, resp);
    }
}
