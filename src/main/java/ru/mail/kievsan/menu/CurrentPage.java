package ru.mail.kievsan.menu;

public interface CurrentPage {
    String getTitle();

    String getView();

    void handle(CurrentChannel channel);
}
