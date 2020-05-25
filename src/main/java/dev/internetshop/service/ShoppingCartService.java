package dev.internetshop.service;

import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    boolean addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    ShoppingCart getByUserId(Long userId);

}
