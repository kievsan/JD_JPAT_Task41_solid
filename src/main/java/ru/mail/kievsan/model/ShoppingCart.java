package ru.mail.kievsan.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ShoppingCart {
    protected final long INIT_COUNT = 1L;
    protected final Map<Product, Long> map;

    public ShoppingCart() {
        map = new HashMap<>();
    }

    public ShoppingCart(ShoppingCart sourceShoppingCart) {
        this();
        map.putAll(sourceShoppingCart.map);
    }

    public void add(Product product) {
        map.merge(product, INIT_COUNT, Long::sum);
    }

    public Optional<Product> findById(Long id) {
        return map.keySet()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
    }

    public Optional<Long> getCount(Product product) {
        return Optional.ofNullable(map.get(product));
    }

    public void setCount(Product product, long count) {
        map.put(product, count);
    }

    public void remove(Product product) {
        map.remove(product);
    }

    public void clear() {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<Entry<Product, Long>> entrySet() {
        return map.entrySet();
    }

    public long getTotal() {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey().getPrice() * e.getValue())
                .collect(Collectors.summarizingLong(v -> v))
                .getSum();
    }

}
