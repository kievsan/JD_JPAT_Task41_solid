package ru.mail.kievsan.menu.impl;

import ru.mail.kievsan.menu.AbstractItemsPage;
import ru.mail.kievsan.model.Product;

import java.util.function.Consumer;

public class ProductsPage extends AbstractItemsPage<Product> {
    private static final String ITEM_LINE_FORMAT = "%-80.80s %6d руб";

    public ProductsPage(String pageTitle, Iterable<Product> productSource, Consumer<Product> itemAction) {
        super(
                pageTitle,
                productSource,
                p -> String.valueOf(p.getId()),
                p -> String.format(ITEM_LINE_FORMAT, p.getName(), p.getPrice()),
                itemAction
        );
    }

}
