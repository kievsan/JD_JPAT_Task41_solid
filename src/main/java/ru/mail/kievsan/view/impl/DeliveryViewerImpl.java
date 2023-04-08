package ru.mail.kievsan.view.impl;

import ru.mail.kievsan.model.Delivery;
import ru.mail.kievsan.view.Viewer;

import java.util.Optional;

public class DeliveryViewerImpl implements Viewer<Delivery> {

    public DeliveryViewerImpl() {
    }

    @Override
    public String toString(Optional<Delivery> optDelivery) {
        return optDelivery
                .map(delivery -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Доставка:").append("\n")
                            .append("Адрес: ").append(delivery.getAddress()).append("\n")
                            .append("Контакт: ").append(delivery.getContact()).append("\n")
                            .append("Стоимость доставки: ").append(delivery.getCost()).append(" руб.");
                    return sb.toString();
                }).orElse("Самовывоз");
    }
}
