package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product table = new Product("table", 1199.99);
        Product chair = new Product("chair", 295.95);
        System.out.println("Display product information about the chair "
                + "before create by productService");
        System.out.println(chair.toString());
        Product armchair = new Product("armchair", 990.90);
        productService.create(table);
        productService.create(chair);
        productService.create(armchair);
        System.out.println("\nDisplay product information about the chair");
        System.out.println(productService.get(chair.getId()));
        System.out.println("\nDisplay information about all the product");
        System.out.println(productService.getAll());
        System.out.println("\nThe result of the removal of the chair from the products");
        System.out.println(productService.delete(armchair.getId()));
        Product sofa = new Product("sofa", 2455.55);
        System.out.println("\nThe result of the removal of the sofa from the products");
        System.out.println(productService.delete(sofa.getId()));
        System.out.println("\nDisplay information about all the product");
        System.out.println(productService.getAll());
        Product tableNew = new Product(1L,"tableNew", 100199.99);
        productService.update(tableNew);
        System.out.println("\nDisplay information about all the product "
                + "after update by product with id = 1");
        System.out.println(productService.getAll());
        table.setPrice(1879.79);
        table.setName("folding table");
        System.out.println("\nDisplay information about all the product without method update");
        System.out.println(productService.getAll());

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = new User("nameUser1","loginUser1", "passwordUser1");
        System.out.println("\nDisplay user information about "
                + "the user1 before create by userService");
        System.out.println(user1.toString());
        userService.create(user1);
        System.out.println("\nDisplay user information about the user1 "
                + "after create by userService and information output through an object");
        System.out.println(user1.toString());
        System.out.println("\nDisplay user information about the user1 after "
                + "create by userService through get");
        System.out.println(userService.get(user1.getId()));
        user1.setName("newNameUser1");
        user1.setLogin("newLoginUser1");
        user1.setPassword("newPasswordUser1");
        userService.update(user1);
        System.out.println("\nDisplay information about all the user without method update user1");
        System.out.println(userService.getAll());
        userService.delete(user1.getId());
        System.out.println("\nDisplay information about all the user after delete user1");
        System.out.println(userService.getAll());
        User user2 = new User("nameUser2","loginUser2", "passwordUser2");
        userService.create(user1);
        userService.create(user2);
        System.out.println("\nDisplay information about all the user after create user1 and user2");
        System.out.println(userService.getAll());

        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart1 = new ShoppingCart(user1);
        System.out.println("\nDisplay shoppingCart information about "
                + "the shoppingCart1 with user1 before create by shoppingCartService");
        System.out.println(shoppingCart1.toString());
        shoppingCartService.addProduct(shoppingCart1, table);
        shoppingCartService.addProduct(shoppingCart1, chair);
        System.out.println("\nDisplay shoppingCart information about "
                + "the shoppingCart1 after addProduct by shoppingCartService");
        System.out.println(shoppingCart1.toString());
        ShoppingCart shoppingCart2 = new ShoppingCart(user1);
        System.out.println("\nDisplay shoppingCart information about "
                + "the shoppingCart2 with user1 before create by shoppingCartService");
        System.out.println(shoppingCart2.toString());
        shoppingCartService.addProduct(shoppingCart2, armchair);
        System.out.println("\nDisplay shoppingCart information about the shoppingCart2 "
                + "after create by shoppingCartService");
        System.out.println(shoppingCart2.toString());
        System.out.println("\nDisplay shoppingCart information about the shoppingCart2 "
                + "after create by shoppingCartService through getAllProducts");
        System.out.println(shoppingCartService.getAllProducts(shoppingCart2));
        shoppingCartService.clear(shoppingCart2);
        System.out.println("\nDisplay shoppingCart information about "
                + "the shoppingCart2 after clear");
        System.out.println(shoppingCart2);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        Order order1 = new Order(user1, shoppingCart1.getProducts());
        System.out.println("\nDisplay order information about the order1 "
                + "with user1 and shoppingCart1 before create by shoppingCartService");
        System.out.println(order1.toString());
        System.out.println("\nDisplay order information user1 before completeOrder with user1");
        System.out.println(orderService.getUserOrders(user1));
        orderService.completeOrder(shoppingCart1.getProducts(), shoppingCart1.getUser());
        System.out.println("\nDisplay order information after completeOrder with shoppingCart1");
        System.out.println(orderService.getAll());
        System.out.println("\nDisplay order information user2 through getUserOrders");
        System.out.println(orderService.getUserOrders(user2));
        System.out.println("\nDisplay order information user1 through getUserOrders");
        System.out.println(orderService.getUserOrders(user1));
        System.out.println("\nDisplay information about product by shoppingCart1 "
                + "through getAllProducts");
        System.out.println(shoppingCartService.getAllProducts(shoppingCart1));
        System.out.println("\nDisplay information about product by shoppingCart1 through toString");
        System.out.println(shoppingCart1.toString());
    }
}
