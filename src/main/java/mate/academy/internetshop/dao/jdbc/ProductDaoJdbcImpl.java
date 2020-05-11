package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);

    @Override
    public Product create(Product product) throws SQLException {
        String query =
                "INSERT INTO products (product_name, product_price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
            LOGGER.info("The product " + product.getId() + " created");
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create the product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProductFromResultSet(resultSet));
            }
            LOGGER.info("The product " + id + " extracted");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract the product #" + id, e);
        }
    }

    @Override
    public Product update(Product product) {
        String query =
                "UPDATE products SET product_name=?, product_price=? WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            LOGGER.info("Updated product " + product.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update the product "
                    + product.getId(), e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted product " + id);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete the product " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        List<Product> allProducts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allProducts.add(getProductFromResultSet(resultSet));
            }
            LOGGER.info("Extracted all products");
            return allProducts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract products", e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("product_name");
        Double productPrice = resultSet.getDouble("product_price");
        return new Product(productId, productName, productPrice);
    }
}
