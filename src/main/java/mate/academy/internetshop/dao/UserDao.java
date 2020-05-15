package mate.academy.internetshop.dao;

import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);

    Set<Role> getUserRoles(Long id);
}
