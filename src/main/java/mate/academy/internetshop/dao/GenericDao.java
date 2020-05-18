package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;

public interface GenericDao<T, K> {
    T create(T element) throws DataProcessingException;

    Optional<T> get(K id) throws DataProcessingException;

    List<T> getAll();

    T update(T element);

    boolean delete(K id);
}
