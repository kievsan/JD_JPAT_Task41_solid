package ru.mail.kievsan.view.impl;

import ru.mail.kievsan.model.Delivery;
import ru.mail.kievsan.model.Order;
import ru.mail.kievsan.model.ShoppingCart;
import ru.mail.kievsan.view.Viewer;

import java.util.Objects;
import java.util.Optional;

public class OrderViewerImpl implements Viewer<Order> {
    protected final Viewer<ShoppingCart> shoppingCartViewer;
    protected final Viewer<Delivery> deliveryViewer;

    public OrderViewerImpl(Viewer<ShoppingCart> shoppingCartViewer, Viewer<Delivery> deliveryViewer) {
        this.shoppingCartViewer = shoppingCartViewer;
        this.deliveryViewer = deliveryViewer;
    }

    @Override
    public String toString(Optional<Order> optOrder) {
        StringBuilder sb = new StringBuilder();
        optOrder.ifPresent(order ->
            sb.append("Заказ ").append(Objects.toString(order.getId(), "Новый")).append(" (").append(order.getOrderStatus()).append(")").append("\n")
                    .append(order.getDateTime()).append("\n")
                    .append("\n")
                    .append(shoppingCartViewer.toString(order.getShoppingCart())).append("\n")
                    .append("\n")
                    .append(deliveryViewer.toString(order.getDelivery())).append("\n")
                    .append("\n")
                    .append("Итог: ").append(order.getTotal()).append(" руб").append("\n")
        );
        return sb.toString();
    }
}
