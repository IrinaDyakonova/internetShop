package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
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
        resp.sendRedirect(req.getContextPath() + "/shoppingCarts/all");
    }
}
