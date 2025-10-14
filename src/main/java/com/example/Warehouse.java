package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();
    private final Map<UUID, Product> products = new HashMap<>();
    private final Set<UUID> changedProducts = Collections.synchronizedSet(new HashSet<>());
    private final String name;

    private Warehouse(String name) {
        this.name = name;

}

public static Warehouse getInstance(String name) {
        return INSTANCES.computeIfAbsent(name, Warehouse::new);
}

public void addProduct(Product product) {
        if(product == null) {

            throw new IllegalArgumentException("Product cannot be null.");
        }
        products.put(product.uuid(), product);
}

public List<Product> getProducts() {

        return List.copyOf(products.values());
}

public Set<UUID> getChangedProducts() {
        return Set.copyOf(changedProducts);
}

public void resetChangedProducts() {
        changedProducts.clear();
}

public Optional<Product> getProduct(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
}

public void updateProductPrice(UUID id, BigDecimal newPrice) {
    Product product = products.get(id);
        if(product == null) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }
        product.price(newPrice);
        changedProducts.add(id);
}

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Perishable> expiredProducts() {
        LocalDate today = LocalDate.now();
        return products.values().stream()
                .filter(p -> p instanceof Perishable)
                .map(p -> (Perishable) p)
                .filter(per -> per.expirationDate().isBefore(today))
                .collect(Collectors.toList());
    }

    public List<Shippable> shippableProducts() {
        return products.values().stream()
                .filter(p -> p instanceof Shippable)
                .map(p -> (Shippable) p)
                .collect(Collectors.toList());
    }

public void remove(UUID id) {
        products.remove(id);
}

public void clearProducts() {
        products.clear();
        changedProducts.clear();
}

public boolean isEmpty() {
        return products.isEmpty();
}

public Map<Category, List<Product>> getProductsGroupedByCategories() {

        return products.values().stream().collect(Collectors.groupingBy(Product::category));
    }

    @Override
    public String toString() {
        return "Warehouse{" + "name='" + name + '\'' + ", products=" + products.size() + '}';
    }


}
