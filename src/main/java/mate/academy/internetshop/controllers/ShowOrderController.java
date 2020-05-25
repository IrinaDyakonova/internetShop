package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;

public class ShowOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final OrderService orderService = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Long id = Long.valueOf(orderId);
        Order order = null;
        try {
            order = orderService.get(id);
        } catch (DataProcessingException e) {
           throw new DataProcessingException("Can't receive order",e);
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/orders/ShowOrderByOneUser.jsp")
                .forward(req, resp);
    }
}
