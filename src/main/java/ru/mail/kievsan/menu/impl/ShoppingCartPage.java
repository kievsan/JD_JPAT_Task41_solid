package ru.mail.kievsan.menu.impl;

import ru.mail.kievsan.menu.ActionsPage;
import ru.mail.kievsan.menu.CurrentChannel;
import ru.mail.kievsan.model.ShoppingCart;
import ru.mail.kievsan.view.Viewer;

public class ShoppingCartPage extends ActionsPage {
    protected final ShoppingCart shoppingCart;
    protected final Viewer<ShoppingCart> shoppingCartViewer;

    public ShoppingCartPage(String pageTitle, ShoppingCart shoppingCart, Viewer<ShoppingCart> shoppingCartViewer) {
        super(pageTitle);
        this.shoppingCart = shoppingCart;
        this.shoppingCartViewer = shoppingCartViewer;
    }

    @Override
    public String getView() {
        return shoppingCartViewer.toString(shoppingCart) + "\n" + super.getView();
    }

    public void removeItemAction(CurrentChannel channel) {
        channel.println("Введите ключ записи для удаления:");
        shoppingCart.findById(Long.parseLong(channel.readLine()))
                .ifPresentOrElse(shoppingCart::remove,
                        () -> channel.println("Не найдено"));
    }

}
