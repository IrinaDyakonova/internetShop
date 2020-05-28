package dev.internetshop.controllers;

import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Injector;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.model.User;
import dev.internetshop.service.ShoppingCartService;
import dev.internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);
    private static final Injector INJECTOR = Injector.getInstance("dev.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");
        String repeatPassword = req.getParameter("pwd-repeat");
        if (password.equals(repeatPassword)) {
            User user;
            try {

                user = userService.create(new User(name, login, password));
                shoppingCartService.create(new ShoppingCart(user.getId()));
            } catch (DataProcessingException throwable) {
                req.setAttribute("massage", throwable.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/exceptionInject.jsp")
                        .forward(req, resp);
            }
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("massage", "Your password and repeat password aren't the same.");
            req.setAttribute("oldName", name);
            req.setAttribute("oldLogin", login);
            LOGGER.warn("Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
