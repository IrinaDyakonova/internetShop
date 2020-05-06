package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User nicole = new User("Nicole", "NicoleSivolap", "06122014");
        nicole.setRoles(Set.of(Role.of("USER")));
        userService.create(nicole);

        User alice = new User("Alice", "AliceSivolap","04092016");
        alice.setRoles(Set.of(Role.of("USER")));
        userService.create(alice);

        User admin = new User("Helen", "admin","19051988");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);

        ShoppingCart shoppingCart = new ShoppingCart(nicole);
        shoppingCartService.create(shoppingCart);
        req.getRequestDispatcher("/WEB-INF/views/users/injectData.jsp").forward(req, resp);
    }
}
