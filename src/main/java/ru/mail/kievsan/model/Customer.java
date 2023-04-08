package ru.mail.kievsan.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
