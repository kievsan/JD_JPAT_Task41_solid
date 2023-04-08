package ru.mail.kievsan;

import ru.mail.kievsan.model.Order;
import ru.mail.kievsan.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {

        Locale.setDefault(new Locale("ru", "RU", ""));

        ConsoleShopApp shop1 = getShop();
        shop1.start("Hi!");

    }

    private static ConsoleShopApp getShop() {
        // TODO: 08.04.2023
        return new ConsoleShopApp(getProducts(), getOrdersList());
    }

    private static List<Product> getProducts() {
        // TODO: 08.04.2023
        return List.of(
                new Product(1L, "Набор ножей", 3700L),
                new Product(2L, "Столовый сервиз", 4815L),
                new Product(3L, "Чайник электрический", 1479L),
                new Product(4L, "Одеяло", 2780L),
                new Product(5L, "Шторы", 3050L),
                new Product(6L, "Подставка для обуви", 999L)
        );
    }

    private static List<Order> getOrdersList() {
        // TODO: 08.04.2023
        return new ArrayList<>();
    }

}
