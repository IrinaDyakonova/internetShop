package mate.academy.internetshop.service;

import java.sql.SQLException;
import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public interface OrderService extends GenericService<Order, Long> {

    Order completeOrder(List<Product> products, Long userId, Long shopCartId) throws SQLException;

    List<Order> getUserOrders(User user);

}
