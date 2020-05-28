package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.Product;
import dev.internetshop.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectProductsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final ProductService productService =
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
            req.setAttribute("massage", "Can't inject these products data into database again");
            req.getRequestDispatcher("/WEB-INF/views/exceptionInject.jsp")
                    .forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/products/fill.jsp")
                .forward(req, resp);
    }
}
