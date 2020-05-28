package dev.internetshop.dao.jdbc;

import dev.internetshop.dao.UserDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Dao;
import dev.internetshop.model.Role;
import dev.internetshop.model.User;
import dev.internetshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE user_login=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            LOGGER.info("User " + login + " extracted");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract user " + login, e);
        }
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query =
                "INSERT INTO users (user_name, user_login, password, salt) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            attachRoleByUser(user);
            LOGGER.info("The user " + user.getId() + " created");
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user", e);
        }
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getUserRoles(user.getId()));
                return Optional.of(user);
            }
            LOGGER.info("User " + id + " extracted");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract user " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allUsers.add(getUserFromResultSet(resultSet));
            }
            LOGGER.info("Extracted all users");
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract users", e);
        }
    }

    @Override
    public User update(User user) {
        String query =
                "UPDATE users SET user_name=?, user_login, password=? WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            LOGGER.info("Updated user " + user.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update user "
                    + user.getId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        deleteUserRoles(id);
        deleteUserShoppingCarts(id);
        deleteUserOrders(id);
        String query = "DELETE FROM users WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted user " + id);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete user " + id, e);
        }
    }

    @Override
    public Set<Role> getUserRoles(Long id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String query = "SELECT role_id, role_name FROM users_roles "
                    + "JOIN roles USING (role_id) WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> result = new HashSet<>();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("role_name"));
                role.setId(resultSet.getLong("role_id"));
                System.out.println(role.getRoleName().toString());
                System.out.println(role.getId());
                result.add(role);
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user roles",e);
        }
    }

    public boolean deleteUserRoles(Long id) {
        String query = "DELETE FROM users_roles WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted user roles" + id);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete user roles " + id, e);
        }
    }

    public void deleteUserShoppingCarts(Long id) {
        String query = "DELETE FROM shopping_carts_products "
                + "WHERE cart_id IN "
                + "(SELECT cart_id FROM shopping_carts WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted all products from user shoppingCart" + id);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Unable to delete all products from user shoppingCarts " + id, e);
        }
        query = "DELETE FROM shopping_carts "
               + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted all shoppingCarts from user " + id);
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to deleteAll shoppingCarts from user "
                    + id, e);
        }

    }

    public void deleteUserOrders(Long id) {
        String query = "DELETE FROM orders_products "
                + "WHERE order_id IN "
                + "(SELECT order_id FROM orders WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted all products from user orders" + id);
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete all products from user orders "
                    + id, e);
        }
        query = "DELETE FROM orders "
                + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Deleted all orders from user " + id);
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete all orders from user " + id, e);
        }

    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String userName = resultSet.getString("user_name");
        String userLogin = resultSet.getString("user_login");
        String userPassword = resultSet.getString("password");
        byte [] userSalt = resultSet.getBytes("salt");
        return new User(userId, userName, userLogin, userPassword, userSalt);
    }

    private boolean attachRoleByUser(User user) {
        String query =
                "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, user.getId());
            for (Role role: user.getRoles()) {
                statement.setLong(2, getRoleIdByName(role.getRoleName()));
                statement.executeUpdate();
            }
            LOGGER.info("The role attached to by user");
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't attach role", e);
        }

    }

    private Long getRoleIdByName(Role.RoleName name) {
        String query = "SELECT role_id FROM roles WHERE role_name = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query);
            statement.setString(1, name.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong("role_id");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't read id role", e);
        }
    }
}
