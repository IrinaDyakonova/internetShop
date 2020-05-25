package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.service.ProductService;
import dev.internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Long userId = (Long) req.getSession().getAttribute("user_id");
        String productId = req.getParameter("id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Product product = null;
        try {
            product = productService.get(Long.valueOf(productId));
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't delete product from cart",e);

        }
        shoppingCartService.deleteProduct(shoppingCart,product);
        resp.sendRedirect(req.getContextPath() + "/shopping-carts/show");
    }
}
