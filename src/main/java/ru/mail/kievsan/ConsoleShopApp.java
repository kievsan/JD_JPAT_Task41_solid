package ru.mail.kievsan;

import ru.mail.kievsan.menu.ActionsPage;
import ru.mail.kievsan.menu.CurrentPage;
import ru.mail.kievsan.menu.CurrentPagesHandler;
import ru.mail.kievsan.menu.CurrentPagesRegister;
import ru.mail.kievsan.model.Delivery;
import ru.mail.kievsan.model.Order;
import ru.mail.kievsan.model.Product;
import ru.mail.kievsan.model.ShoppingCart;
import ru.mail.kievsan.view.Viewer;
import ru.mail.kievsan.view.ViewersRegister;
import ru.mail.kievsan.view.impl.DeliveryViewerImpl;
import ru.mail.kievsan.view.impl.OrderViewerImpl;
import ru.mail.kievsan.view.impl.ShoppingCartViewerImpl;
import ru.mail.kievsan.menu.impl.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConsoleShopApp implements ViewersRegister, CurrentPagesRegister {

    protected final String description;
    protected final List<Product> productRepository;
    protected final List<Order> ordersRepository;
    protected final ShoppingCart shoppingCart;
    protected final CurrentPagesHandler session;

    private Map<Class<?>, Viewer<?>> viewersMap = new HashMap<>();
    private Map<String, CurrentPage> pagesMap = new HashMap<>();

    public ConsoleShopApp(List<Product> productRepository, List<Order> ordersRepository) {
        this.description = "anyshop@any.bisness";
        this.productRepository = productRepository;
        this.ordersRepository = ordersRepository;
        shoppingCart = new ShoppingCart();
        session = new CurrentPagesHandler(System.in, System.out);
        configure();
    }

    protected void configure() {
        configureViewers();
        configureMenu(session);
    }

    protected void configureViewers() {
        DeliveryViewerImpl deliveryViewer = new DeliveryViewerImpl();
        registerViewerFor(Delivery.class, deliveryViewer);

        ShoppingCartViewerImpl shoppingCartViewer = new ShoppingCartViewerImpl();
        registerViewerFor(ShoppingCart.class, shoppingCartViewer);

        OrderViewerImpl orderViewer = new OrderViewerImpl(shoppingCartViewer, deliveryViewer);
        registerViewerFor(Order.class, orderViewer);
    }

    protected void configureMenu(CurrentPagesHandler session) {
        ActionsPage mainPage = new ActionsPage(this + ":\nГлавное меню");
        mainPage.add("1", "Товары", () -> setPage("product"));
        mainPage.add("2", "Заказы", () -> setPage("orders"));
        mainPage.add("3", "Корзина", () -> setPage("shopping_cart"));
        mainPage.add("x", "Выход", () -> stop("Chao!"));
        registerPage("main", mainPage);

        ProductsPage productPage = new ProductsPage("Список товаров", productRepository, p -> {
            shoppingCart.add(p);
            session.println("Добавлено в корзину: " + p.getName() + ". Всего: " + shoppingCart.getCount(p).orElse(0L) + " шт.");
        });
        productPage.add("x", "Выход", () -> setPage("main"));
        registerPage("product", productPage);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage("Корзина", shoppingCart, getViewerFor(ShoppingCart.class));
        shoppingCartPage.add("=", "Оформить заказ", () -> setPage("make_order"));
        shoppingCartPage.add("-", "Удалить строку", () -> shoppingCartPage.removeItemAction(session));
        shoppingCartPage.add("x", "Выход", () -> setPage("main"));
        registerPage("shopping_cart", shoppingCartPage);

        OrdersPage ordersPage = new OrdersPage("Просмотр заказов", ordersRepository, order -> {
            System.out.println("showOrder");
            ShowOrderPage showOrderPage = new ShowOrderPage(order, getViewerFor(Order.class));
            showOrderPage.add("x", "Выход", () -> setPage("main"));
            session.setPage(showOrderPage);
        });
        ordersPage.add("x", "Выход", () -> setPage("main"));
        registerPage("orders", ordersPage);

        MakeOrderPage makeOrderPage = new MakeOrderPage("Оформление заказа", shoppingCart, getViewerFor(Order.class),
                optOrder -> optOrder.ifPresentOrElse(
                        order -> {
                            order.setId((long) ordersRepository.size());
                            ordersRepository.add(order);
                            session.println("Заказ сохранен.");
                            shoppingCart.clear();
                            setPage("orders");
                        },
                        () -> setPage("shopping_cart"))
        );
        registerPage("make_order", makeOrderPage);

        session.setPage(mainPage);
    }

    @Override
    public <T> Viewer<T> getViewerFor(Class<T> clazz) {
        return (Viewer<T>) viewersMap.get(clazz);
    }

    @Override
    public <T> void registerViewerFor(Class<T> clazz, Viewer<T> viewer) {
        viewersMap.put(clazz, viewer);
    }

    @Override
    public CurrentPage getPage(String name) {
        return pagesMap.get(name);
    }

    @Override
    public void registerPage(String nameId, CurrentPage page) {
        pagesMap.put(nameId, page);
    }

    public void setPage(String name) {
        session.setPage(getPage(name));
    }

    public void start(String msg) {
        System.out.println("Открыт " + this + "\n" + msg + "\n");
        session.mainLoop();
    }

    public void stop(String msg) {
        try {
            System.out.println("\n" + this + " закрыт\n" + msg);
            session.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return "Магазин '" + this.description + "'";
    }
}
