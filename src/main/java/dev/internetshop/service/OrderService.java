package dev.internetshop.service;

import dev.internetshop.model.Order;
import dev.internetshop.model.Product;
import dev.internetshop.model.User;
import java.sql.SQLException;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {

    Order completeOrder(List<Product> products, Long userId, Long shopCartId) throws SQLException;

    List<Order> getUserOrders(User user);

}
