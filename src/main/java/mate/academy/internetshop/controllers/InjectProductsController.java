package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            productService.create(orange);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            productService.create(mango);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            productService.create(coconut);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/views/products/injectDataProducts.jsp")
                .forward(req, resp);
    }
}
