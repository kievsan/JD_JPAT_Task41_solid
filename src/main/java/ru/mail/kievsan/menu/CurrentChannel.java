package ru.mail.kievsan.menu;

public interface CurrentChannel {
    String readLine();

    void write(Object obj);

    default void println(Object obj) {
        write(String.valueOf(obj));
        write("\n");
    }

}
