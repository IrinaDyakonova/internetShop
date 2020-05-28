package dev.internetshop.service;

import dev.internetshop.exceptions.DataProcessingException;
import java.util.List;

public interface GenericService<T, K> {
    T create(T t) throws DataProcessingException;

    T get(K id) throws DataProcessingException;

    List<T> getAll();

    T update(T element);

    boolean delete(K id);
}
