package dev.internetshop.service.impl;

import dev.internetshop.dao.OrderDao;
import dev.internetshop.dao.ShoppingCartDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Inject;
import dev.internetshop.lib.Service;
import dev.internetshop.model.Order;
import dev.internetshop.model.Product;
import dev.internetshop.model.User;
import dev.internetshop.service.OrderService;
import dev.internetshop.service.ShoppingCartService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, Long userId, Long shopCartId) {
        shoppingCartDao.delete(shopCartId);
        Order order = orderDao.create(new Order(userId, products));
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao
                .getAll()
                .stream()
                .filter(order -> order.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order create(Order order)
            throws DataProcessingException {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id)
            throws DataProcessingException {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
