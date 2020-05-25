package dev.internetshop.dao.jdbc;

import dev.internetshop.dao.ShoppingCartDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Dao;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartDaoJdbcImpl.class);

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query =
                "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
            LOGGER.info("The shoppingCart " + shoppingCart.getId() + " created");
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shoppingCart", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id=? "
                + "LEFT JOIN shopping_carts_products USING (cart_id) "
                + "LEFT JOIN products USING (product_id)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                return Optional.of(shoppingCart);
            }
            LOGGER.info("The shoppingCart " + id + " extracted");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract the shoppingCart " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts ";
        List<ShoppingCart> allShoppingCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allShoppingCarts
                        .add(new ShoppingCart(
                                resultSet.getLong("cart_id"),
                                resultSet.getLong("user_id"),
                                getAllProducts(resultSet.getLong("cart_id"))));
            }
            LOGGER.info("Extracted all shoppingCarts");
            return allShoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract shoppingCarts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteAllProductFromShoppingCart(shoppingCart.getId());
        String query = "INSERT INTO shopping_carts_products(card_id, product_id) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Update shoppingCart failed", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        deleteAllProductFromShoppingCart(id);
        return true;
    }

    @Override
    public boolean addProductToShoppingCart(Long productId, Long shoppingCartId) {
        String query =
                "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            statement.setLong(2, productId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add the product to shoppingCart", e);
        }
    }

    @Override
    public boolean deleteProductFromShoppingCart(Long productId, Long shoppingCartId) {
        String query =
                "DELETE FROM shopping_carts_products WHERE cart_id = ? AND product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            statement.setLong(2, productId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the product from shoppingCart", e);
        }
    }

    @Override
    public boolean deleteAllProductFromShoppingCart(Long shoppingCartId) {
        String query =
                "DELETE FROM shopping_carts_products WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete all products from shoppingCart", e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long shoppingCartId = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> productsShoppingCart = new ArrayList<>();
        String name;
        Double price;
        Long id;
        while (resultSet.next()) {
            name = resultSet.getString("product_name");
            price = resultSet.getDouble("product_price");
            id = resultSet.getLong("product_id");
            productsShoppingCart.add(new Product(id, name, price));
        }
        return new ShoppingCart(shoppingCartId, userId, productsShoppingCart);
    }

    private List<Product> getAllProducts(Long cartId) {
        String query = "SELECT * FROM shopping_carts_products "
                + "LEFT JOIN products USING (product_id) "
                + "WHERE cart_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
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
            throw new DataProcessingException("Unable to receive list of products ", e);
        }

    }
}
