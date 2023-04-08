package ru.mail.kievsan.menu.impl;

import ru.mail.kievsan.menu.AbstractPage;
import ru.mail.kievsan.menu.CurrentChannel;
import ru.mail.kievsan.model.Delivery;
import ru.mail.kievsan.model.Order;
import ru.mail.kievsan.model.ShoppingCart;
import ru.mail.kievsan.service.DeliveryService;
import ru.mail.kievsan.service.DeliveryServiceImpl;
import ru.mail.kievsan.view.Viewer;

import ru.mail.kievsan.model.OrderBuilder;

import java.util.Optional;
import java.util.function.Consumer;


public class MakeOrderPage extends AbstractPage {
    protected final Consumer<Optional<Order>> newOrderConsumer;
    protected final ShoppingCart shoppingCart;
    protected final Viewer<Order> orderViewer;

    public MakeOrderPage(
            String pageTitle,
            ShoppingCart shoppingCart,
            Viewer<Order> orderViewer,
            Consumer<Optional<Order>> newOrderConsumer
    ) {
        super(pageTitle);
        this.shoppingCart = shoppingCart;
        this.orderViewer = orderViewer;
        this.newOrderConsumer = newOrderConsumer;
    }

    @Override
    public String getView() {
        return "";
    }

    protected DeliveryService getDeliveryService() {
        return DeliveryServiceImpl.getInstance();
    }

    @Override
    public void handle(CurrentChannel channel) {
        if (shoppingCart.isEmpty()) {
            channel.println("Невозможно создать заказ. Корзина пуста.");
            return;
        }

        OrderBuilder orderBuilder = new OrderBuilder();

        channel.println("Введите адрес доставки (оставьте пустым для самовывоза):");
        String address = channel.readLine();
        if (!address.isEmpty()) {
            Delivery delivery = getDeliveryService().getByAddress(address);

            channel.println("Введите имя получателя: ");
            String contact = channel.readLine();
            delivery.setContact(contact);

            orderBuilder.delivery(delivery);
        }

        orderBuilder.shoppingCart(new ShoppingCart(shoppingCart));
        Order order = orderBuilder.build();

        channel.println(orderViewer.toString(order));

        channel.println("Подтвердите заказ (оставьте пустым для отмены):");
        String inputStr = channel.readLine();
        if (inputStr.isEmpty()) {
            channel.println("Оформление заказа прервано.");
            newOrderConsumer.accept(Optional.empty());
        } else {
            newOrderConsumer.accept(Optional.of(order));
        }
    }
}
