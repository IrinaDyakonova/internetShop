package dev.internetshop.controllers;

import dev.internetshop.lib.Injector;
import dev.internetshop.model.Role;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.model.User;
import dev.internetshop.service.ShoppingCartService;
import dev.internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User("User", "user", "1");
        userService.create(user);
        shoppingCartService.create(new ShoppingCart(user.getId()));

        User admin = new User("Admin", "admin","1");
        admin.getRoles().add((Role.of("ADMIN")));
        userService.create(admin);
        shoppingCartService.create(new ShoppingCart(admin.getId()));

        req.getRequestDispatcher("/WEB-INF/views/users/injectData.jsp").forward(req, resp);
    }
}
