package dev.internetshop.dao;

import dev.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    boolean addProductToShoppingCart(Long productId, Long shoppingCartId);

    boolean deleteProductFromShoppingCart(Long productId, Long shoppingCartId);

    boolean deleteAllProductFromShoppingCart(Long shoppingCartId);
}
