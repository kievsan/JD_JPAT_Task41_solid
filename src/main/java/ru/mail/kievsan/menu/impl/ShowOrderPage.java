package ru.mail.kievsan.menu.impl;

import ru.mail.kievsan.menu.ActionsPage;
import ru.mail.kievsan.model.Order;
import ru.mail.kievsan.view.Viewer;

public class ShowOrderPage extends ActionsPage {
    protected final Order order;
    protected final Viewer<Order> orderViewer;

    public ShowOrderPage(Order order, Viewer<Order> orderViewer) {
        super("Просмотр заказа");
        this.order = order;
        this.orderViewer = orderViewer;
    }

    @Override
    public String getView() {
        return orderViewer.toString(order) + "\n" + super.getView();
    }
}
