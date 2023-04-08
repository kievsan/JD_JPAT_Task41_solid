package ru.mail.kievsan.menu;

public interface CurrentPagesRegister {
    CurrentPage getPage(String name);

    void registerPage(String nameId, CurrentPage page);
}
