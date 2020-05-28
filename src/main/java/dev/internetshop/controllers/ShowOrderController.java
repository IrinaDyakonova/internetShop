package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.Order;
import dev.internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

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
