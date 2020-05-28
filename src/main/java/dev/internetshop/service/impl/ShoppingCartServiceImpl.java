package dev.internetshop.service.impl;

import dev.internetshop.dao.ShoppingCartDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Inject;
import dev.internetshop.lib.Service;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
import dev.internetshop.service.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public boolean addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.addProductToShoppingCart(product.getId(), shoppingCart.getId());
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCartDao.deleteProductFromShoppingCart(product.getId(), shoppingCart.getId());
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao
                .getAll()
                .stream()
                .filter(s -> s.getUserId().equals(userId))
                .findFirst()
                .get();
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart)
            throws DataProcessingException {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart get(Long id) throws DataProcessingException {
        return shoppingCartDao.get(id).get();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return shoppingCartDao.getAll();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartDao.delete(id);
    }
}
