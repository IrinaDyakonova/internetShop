package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.exceptions.DataProcessingException;

public interface GenericService<T, K> {
    T create(T t) throws DataProcessingException;

    T get(K id) throws DataProcessingException;

    List<T> getAll();

    T update(T element);

    boolean delete(K id);
}
