package dev.internetshop.dao;

import dev.internetshop.model.Role;
import dev.internetshop.model.User;
import java.util.Optional;
import java.util.Set;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);

    Set<Role> getUserRoles(Long id);
}
