package ru.geekbrains.smartkt.prefs;

// Prefaces ("префисиз") - буквально - "введения",
// почему так - за давностью лет (началось с 2013 или 2014 года) уже не вспомню
public class Prefs {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String ITEM_NAME = "Товар";
    public static final String PRICE_NAME = "Цена";
    public static final String AMOUNT_NAME = "Количество";
    public static final String PROVIDER_NAME = "Производитель/поставщик "+ITEM_NAME.toLowerCase()+"а";
    public static final String ORDER_NAME = "Заказ";
    public static final String USER_NAME = "Пользователь";

    public static final String ERR_NOT_FOUND = "%s не найден%s";
    public static final String ERR_INVALID_VALUE_OF = "Недопустим%s %s %s";

    public static final String ERR_ITEM_NOT_FOUND = String.format(ERR_NOT_FOUND, ITEM_NAME, "");
    public static final String ERR_CANNOT_UPDATE = "Обновление товара в БД невозможно - "
            + ERR_ITEM_NOT_FOUND.toLowerCase();
    public static final String ERR_ORDER_NOT_FOUND = String.format(ERR_NOT_FOUND, ORDER_NAME, "");
    public static final String ERR_USER_NOT_FOUND = String.format(ERR_NOT_FOUND, USER_NAME, "");
    public static final String ERR_NO_SUCH_ITEM_IN_ORDER =
            String.format(ERR_NOT_FOUND, ITEM_NAME, "в "+ORDER_NAME+"е");

    public static final String ERR_INVALID_PRODUCT_PRICE =
            String.format(ERR_INVALID_VALUE_OF, "ая", PRICE_NAME.toLowerCase(), ITEM_NAME.toLowerCase()+"а");
    public static final String ERR_INVALID_PRODUCT_AMOUNT =
            String.format(ERR_INVALID_VALUE_OF, "ое", AMOUNT_NAME.toLowerCase(), ITEM_NAME.toLowerCase()+"а");
    public static final String ERR_MUST_HAVE_TITLE = "%s не может не иметь названия";
    public static final String ERR_MUST_HAVE_NAME = USER_NAME + " не может не иметь имени";
    public static final String ERR_MUST_HAVE_ROLE = "Зарегистрированный пользователь должен иметь права";
    public static final String ERR_WRONG_AUTH = "Недопустимое имя пользователя или пароль";
    public static final String ERR_TOKEN_EXPIRED = "Срок действия токена истек";

    // состояния заказа
    public static final int ORDER_STATUS_REGISTERED = 1;
    public static final int ORDER_STATUS_CREATING = 2;
    public static final int ORDER_STATUS_FORMED = 3;
    public static final int ORDER_STATUS_DELIVERED = 4;
    // вряд ли ради этих строк есть смысл создавать отдельную таблицу БД.
    // чтобы не нарушать модель данных БД, в модели таблица останется.
    // стоит ли ее физически создавать и обращаться к ней для выборки -
    // скорее нет, чем да
    public static final String[] ORDER_STATUS = {
        "Принят", "Формируется", "Готов к выдаче", "Получен"
    };
}