package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class InjectProductsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product kiwi = new Product("kiwi",49.99);
        Product orange = new Product("orange",34.99);
        Product mango = new Product("mango",75.99);
        Product coconut = new Product("coconut",99.99);
        try {
            productService.create(kiwi);
            productService.create(orange);
            productService.create(mango);
            productService.create(coconut);
        } catch (DataProcessingException throwable) {
            req.setAttribute("massage", "Don't inject products data controller");
            req.getRequestDispatcher("/WEB-INF/views/exceptionInject.jsp")
                    .forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/views/products/injectDataProducts.jsp")
                .forward(req, resp);
    }
}
