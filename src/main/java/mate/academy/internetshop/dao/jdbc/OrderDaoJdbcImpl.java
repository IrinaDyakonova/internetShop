package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    @Override
    public Order create(Order order) {
        String query =
                "INSERT INTO orders (user_id) VALUES (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            for (Product product : order.getProducts()) {
                addProductToOrder(product.getId(), order.getId());
            }
            LOGGER.info("The order " + order.getId() + " created");
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders "
                + "LEFT JOIN orders_products USING (order_id) "
                + "LEFT JOIN products USING (product_id) "
                + "WHERE orders.order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                return Optional.of(order);
            }
            LOGGER.info("The order " + id + " extracted");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract the order " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders";
        List<Order> allOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allOrders
                        .add(new Order(resultSet.getLong("order_id"),
                                resultSet.getLong("user_id"),
                                getAllProducts(resultSet.getLong("order_id"))));
            }
            LOGGER.info("Extracted all orders");
            return allOrders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract orders", e);
        }
    }

    @Override
    public Order update(Order element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        deleteAllProductByOrder(id);
        String query = "DELETE FROM orders WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted order " + id);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete order " + id, e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> productsOrder = new ArrayList<>();
        String name;
        Double price;
        Long id;
        do {
            name = resultSet.getString("product_name");
            price = resultSet.getDouble("product_price");
            id = resultSet.getLong("product_id");
            productsOrder.add(new Product(id, name, price));
        } while (resultSet.next());
        return new Order(orderId, userId, productsOrder);
    }

    private boolean deleteAllProductByOrder(Long orderId) {
        String query =
                "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the product by order", e);
        }
    }

    private boolean addProductToOrder(Long productId, Long orderId) {
        String query =
                "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.setLong(2, productId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add the product to order", e);
        }
    }

    private List<Product> getAllProducts(Long orderId) {
        String query = "SELECT * FROM orders_products "
                + "LEFT JOIN products USING (product_id) "
                + "WHERE order_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products
                        .add(new Product(resultSet.getLong("product_id"),
                                resultSet.getString("product_name"),
                                resultSet.getDouble("product_price")));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract all product from order", e);
        }

    }
}
