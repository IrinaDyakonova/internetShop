package dev.internetshop.dao;

import dev.internetshop.exceptions.DataProcessingException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T element) throws DataProcessingException;

    Optional<T> get(K id) throws DataProcessingException;

    List<T> getAll();

    T update(T element);

    boolean delete(K id);
}
