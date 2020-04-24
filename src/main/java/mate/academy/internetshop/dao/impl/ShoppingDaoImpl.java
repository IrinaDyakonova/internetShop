package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage
                .shoppingCarts
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {

        ShoppingCart shoppingCartThatNeedUpdate = get(shoppingCart
                .getId())
                .get();

        shoppingCartThatNeedUpdate.setProducts(shoppingCart.getProducts());
        shoppingCartThatNeedUpdate.setUser(shoppingCart.getUser());
        return shoppingCartThatNeedUpdate;
    }

    @Override
    public boolean delete(Long id) {
        return Storage
                .shoppingCarts
                .removeIf(s -> s.getId().equals(id));
    }
}
