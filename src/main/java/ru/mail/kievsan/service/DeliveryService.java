package ru.mail.kievsan.service;

import ru.mail.kievsan.model.Delivery;

public interface DeliveryService {
    Delivery getByAddress(String address);
}
