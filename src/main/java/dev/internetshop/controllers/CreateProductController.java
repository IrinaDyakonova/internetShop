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

public class CreateProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/create.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        try {
            productService.create(new Product(name,Double.parseDouble(price)));
            resp.sendRedirect(req.getContextPath() + "/admin/products/list");
        } catch (DataProcessingException throwable) {
            req.setAttribute("message", throwable.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/exceptionInject.jsp")
                    .forward(req, resp);
        }
    }
}
