package ru.mail.kievsan.view;

import java.util.Optional;

@FunctionalInterface
public interface Viewer<T> {
    String toString(Optional<T> obj);

    default String toString(T obj){
        return toString(Optional.ofNullable(obj));
    }
}
