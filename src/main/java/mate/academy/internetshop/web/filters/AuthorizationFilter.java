package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/showProductsAdmin", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/injectDataProducts", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/shoppingCarts/all", Set.of(Role.RoleName.USER));
        protectedUrls.put("/orders/add", Set.of(Role.RoleName.USER));
        protectedUrls.put("/orders/order", Set.of(Role.RoleName.USER));
        protectedUrls.put("/products/all", Set.of(Role.RoleName.USER));

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();
        if (protectedUrls.get(requestedUrl) == null) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute("user_id");
        if (userId == null) {
            resp.sendRedirect("/login");
            return;
        }
        User user = null;
        try {
            user = userService.get(userId);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't extract user " + userId, e);
        }
        if (isAuthorized(user, protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            LOGGER.warn("User login = " + user.getLogin()
                    + " and id = " + userId + " does not have access to the page");
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        System.out.println("userRole.size " + user.getRoles().size());

        for (Role.RoleName authorizedRole: authorizedRoles) {
            System.out.println("! - " + authorizedRole.toString());
            for (Role userRole: user.getRoles()) {
                System.out.println("!!! - " + userRole.getRoleName().toString());
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
