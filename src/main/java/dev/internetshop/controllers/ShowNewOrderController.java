package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowNewOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        try {
            orderService.get(userId);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't creat order",e);
        }
        req.getRequestDispatcher("/WEB-INF/views/orders/recent.jsp")
                .forward(req, resp);
    }
}

