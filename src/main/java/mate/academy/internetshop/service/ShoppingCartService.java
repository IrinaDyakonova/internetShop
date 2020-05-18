package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    boolean addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    ShoppingCart getByUserId(Long userId);

}
