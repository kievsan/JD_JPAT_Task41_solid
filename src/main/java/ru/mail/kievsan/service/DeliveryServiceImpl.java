package ru.mail.kievsan.service;

import ru.mail.kievsan.model.Delivery;

public class DeliveryServiceImpl implements DeliveryService {
    private static DeliveryServiceImpl INSTANCE;
    protected final int MAX_DELIVARY_COST = 5_000;

    private DeliveryServiceImpl() {
    }

    public static DeliveryServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeliveryServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Delivery getByAddress(String address) {
        long cost = Math.abs(address.hashCode() % MAX_DELIVARY_COST);
        return new Delivery(address, cost);
    }
}
