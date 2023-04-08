package ru.mail.kievsan.model;

public enum OrderStatus {
    NEW("Новый"),
    CANCELED("Отменен"),
    CONFIRMED("Подтвержден"),
    COLLECTED("Собран"),
    READY_FOR_PICKUP("Готов к выдаче"),
    READY_TO_DELIVERY("Готов к доставке"),
    DELIVERY("Доставка"),
    COMPLETED("Завершен");

    private String statusText;

    OrderStatus(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return statusText;
    }
}
