package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.Order;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.service.OrderService;
import dev.internetshop.service.ShoppingCartService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        List<Product> products = List.copyOf(shoppingCart.getProducts());
        Order order = null;
        try {
            order = orderService
                    .completeOrder(products, shoppingCart.getUserId(), shoppingCart.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't creat order",e);
        }
        req.setAttribute("order", order);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orders/recent.jsp")
                .forward(req, resp);
    }
}
