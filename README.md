## Консольное приложение "Магазин"

Возможности программы:

- Вывод доступных для покупки товаров
- Составление продуктовой корзины пользователя
- Оформление заказа с учетом доставки
- Трекинг заказа в системе доставки

### Примеры избегания магических чисел

1. Класс `DeliveryServiceImpl` использует константу [MAX_DELIVERY_COST](https://github.com/kievsan/jd_jpat_task41_solid/service/DeliveryServiceImpl.java#L21) для ограничения максимального значения генерируемой стоимости доставки.

2. Класс `ShoppingCart` использует константу [INIT_COUNT](https://github.com/kievsan/jd_jpat_task41_solid/model/ShoppingCart.java#L24) как первоначальное значение счетчика определенного товара в корзине.

### Примеры использования принципа DRY (Don’t Repeat Yourself)

1. Пакет классов для создания интерактивных страниц консольного меню `kievsan.menu` построен через дополнение уже существующих классов наследованием или передачей функций в параметрах.
  - Класс `kievsan.menu.impl.ActionsPage` умеет выводить на экран список возможных команд с идентификаторами и выполнять их. Класс `ShoppingCartPage` расширяет класс `ActionsPage` и с минимальными изменениями [добавляет](https://github.com/kievsan/jd_jpat_task41_solid/menu/impl/ShoppingCartPage.java#L20) возможность выводить список товаров в корзине. Нам не приходится снова реализовывать вывод меню заданных команд.
2. При выводе на экран текстового представления `Order` используется функционал класса `OrderPresenterImpl`. Класс сам использует другие преобразователи, получаемые из параметров: `ShoppingCartPresenterImpl` и `DeliveryPresenterImpl`. Так мы [избегаем повторения](https://github.com/kievsan/jd_jpat_task41_solid/view/impl/OrderPresenterImpl.java#L27) реализации функций преобразования.

### Примеры применения принципов SOLID

#### Single Responsibility Principle

1. Все классы пакета `kievsan.model` предназначены только для хранения и передачи данных. Они ничего не выводят на экран. Не вызывают системные методы. Не читают ввод. Например класс [Delivery](https://github.com/kievsan/jd_jpat_task41_solid/model/Delivery.java).

2. Классы пакета `kievsan.view.impl` предназначены только для создания текстового представления соответствующих POJO моделей (`Order`, `Delivery`, `ShoppingCart`). Они не обрабатывают пользовательский ввод. Ничего не выводят на экран. Не изменяют переданные им объекты. Например класс [DeliveryPresenterImpl](https://github.com/kievsan/jd_jpat_task41_solid/view/impl/DeliveryPresenterImpl.java).

#### Open Closed Principle

1. Класс `kievsan.menu.ActionListPage` выводит обрабатываемые команды в виде меню. Его наследник `kievsan.menu.ShowOrderPage` переопределяет метод [.getView()](https://github.com/kievsan/jd_jpat_task41_solid/menu/impl/ShowOrderPage.java#L18) и выводит текстовое представление `Order` до пунктов меню с командой `Выход`.

2. Класс `kievsan.ConsoleShopApplication` спроектирован так, чтобы предоставить его наследникам возможность расширения функционала без изменения исходного класса. Например переопределение метода [.configureMenu()](https://github.com/kievsan/jd_jpat_task41_solid/ConsoleShopApplication.java#L57)  позволит создать свой набор интерактивных экранов. А переопределение метода [.configureViewers()](https://github.com/kievsan/jd_jpat_task41_solid/ConsoleShopApplication.java#L46) позволит дополнить набор преобразователей для новых объектов.

#### Liskov Substitution Principle

1. Есть цепочка наследования классов и интерфейсов:
```
   CurrentPage      - базовый интерфейс
        |
   AbstractPage     - хранит базовые данные
        |
   ActionsPage      - выводит список команд и реагирует на них
        |
AbstractItemstPage  - выводит список элементов и команд. Реагирует на команды.
        |
   ProductsPage     - выводит список товаров из источника + весь функционал выше
```
Класс `kievsan.menu.InteractivePagesHandler` занимается сменой страниц, передачей им пользовательского ввода и передачи результата. Ему для работы достаточно знать, что они потомки `InteractivePage`. Так выполняется принцип, когда [потомки могут замещать предка](https://github.com/kievsan/jd_jpat_task41_solid/menu/CurrentPagesHandler.java#L23).

#### Interface Segregation Principle

Класс `kievsan.ConsoleShopApplication` реализует интерфейсы [ViewersRegister](https://github.com/kievsan/jd_jpat_task41_solid/view/ViewersRegister.java) и [CurrentPagesRegister](https://github.com/kievsan/jd_jpat_task41_solid/menu/CurrentPagesRegister.java). Они спроектированы минималистично на случай если функцию регистра объектов будет выполнять внешний класс. В таком случае при реализации одного регистра, не придется имплементировать методы относящиеся ко второму.

#### Dependency Inversion Principle

Объекту класса `kievsan.view.impl.OrderPresenterImpl` в работе требуется представить в текстовом виде объекты `ShoppingCart` и `Delivery`.

Он не создает экземпляры `ShoppingCartPresenterImpl` и `DeliveryPresenterImpl` самостоятельно, чтобы не зависеть от конкретных классов и реализации. `DeliveryPresenterImpl` получает необходимые ему объекты через параметры конструктора.

Готовые объекты внедряются через [использование интерфейса](https://github.com/kievsan/jd_jpat_task41_solid/view/impl/OrderViewerImpl.java#L15) `Presenter<T>`. Поэтому внедрить можно любую реализацию подходящую по типу.