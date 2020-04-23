package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product table = new Product("table", 1199.99);
        Product chair = new Product("chair", 295.95);
        Product armchair = new Product("armchair", 990.90);
        productService.create(table);
        productService.create(chair);
        productService.create(armchair);
        System.out.println(productService.get(chair.getId()));
        System.out.println(productService.getAll());
        System.out.println(productService.delete(armchair.getId()));
        Product sofa = new Product("sofa", 2455.55);
        System.out.println(productService.delete(sofa.getId()));
        System.out.println(productService.getAll());
        Product tableNew = new Product(1L,"tableNew", 100199.99);
        productService.update(tableNew);
        System.out.println(productService.getAll());
        table.setPrice(1879.79);
        table.setName("folding table");
        System.out.println(productService.getAll());
    }
}
