package ru.mail.kievsan.model;

import java.time.ZonedDateTime;
import java.util.Optional;

public class OrderBuilder {
    private ShoppingCart shoppingCart;
    private Optional<Delivery> delivery = Optional.empty();
    private OrderStatus orderStatus = OrderStatus.NEW;

    public OrderBuilder shoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }

    public OrderBuilder delivery(Delivery delivery) {
        this.delivery = Optional.of(delivery);
        return this;
    }

    public Order build() {
        return new Order(null, ZonedDateTime.now(), shoppingCart, delivery, orderStatus);
    }

}
