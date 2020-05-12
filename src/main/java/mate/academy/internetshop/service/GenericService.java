package mate.academy.internetshop.service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T, K> {
    T create(T t) throws SQLException;

    T get(K id) throws SQLException;

    List<T> getAll();

    T update(T element);

    boolean delete(K id);
}
