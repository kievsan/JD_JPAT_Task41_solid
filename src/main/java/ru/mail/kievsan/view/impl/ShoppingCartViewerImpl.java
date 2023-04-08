package ru.mail.kievsan.view.impl;

import ru.mail.kievsan.model.Product;
import ru.mail.kievsan.model.ShoppingCart;
import ru.mail.kievsan.view.Viewer;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartViewerImpl implements Viewer<ShoppingCart> {
    protected String ITEM_LINE_FORMAT = "%d\t%-80.80s %d шт x %d руб = %d руб";

    public ShoppingCartViewerImpl() {
    }

    @Override
    public String toString(Optional<ShoppingCart> optShoppingCart) {
        return optShoppingCart
                .map(shoppingCart -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append(
                            shoppingCart.entrySet()
                                    .stream()
                                    .sorted(Comparator.comparing(e -> e.getKey().getId()))
                                    .map(e -> {
                                        Product product = e.getKey();
                                        long count = e.getValue();
                                        long total = product.getPrice() * count;
                                        return String.format(ITEM_LINE_FORMAT,
                                                product.getId(), product.getName(), count, product.getPrice(), total);
                                    }).collect(Collectors.joining("\n"))
                    ).append("\n").append("Сумма: ").append(shoppingCart.getTotal()).append(" руб");
                    return sb.toString();
                }).orElse("");
    }
}
