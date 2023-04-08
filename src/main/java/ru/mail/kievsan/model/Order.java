package ru.mail.kievsan.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@Setter
public class Order {
    private final ShoppingCart shoppingCart;
    private Long id;
    private ZonedDateTime dateTime;
    private final Optional<Delivery> delivery;
    private OrderStatus orderStatus;

    public Order(Long id, ZonedDateTime dateTime, ShoppingCart shoppingCart, Optional<Delivery> delivery, OrderStatus orderStatus) {
        this.id = id;
        this.dateTime = dateTime;
        this.shoppingCart = shoppingCart;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }

    public Order(ShoppingCart shoppingCart, Optional<Delivery> delivery) {
        this(null, ZonedDateTime.now(), shoppingCart, delivery, OrderStatus.NEW);
    }

    public long getTotal() {
        long result = shoppingCart.getTotal();
        if (delivery.isPresent()) {
            result += delivery.get().getCost();
        }
        return result;
    }
}
