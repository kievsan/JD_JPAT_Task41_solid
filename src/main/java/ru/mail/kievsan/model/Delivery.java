package ru.mail.kievsan.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delivery {
    private final String address;
    private final long cost;
    private String contact;

    public Delivery(String address, long cost) {
        this("", address, cost);
    }

    public Delivery(String contact, String address, long cost) {
        this.contact = contact;
        this.address = address;
        this.cost = cost;
    }


}
