package ru.geekbrains.smartkt.prefs;

// Prefaces ("префисиз") - буквально - "введения",
// почему так - за давностью лет (началось с 2013 или 2014 года) уже не вспомню
public class Prefs {
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String ERR_PRODUCT_NOT_FOUND = "Товар не найден";
    public static final String ERR_NOT_FOUND = "%s не найден%s";
    public static final String ERR_CANNOT_UPDATE = "Обновление товара в БД невозможно - "
            + ERR_PRODUCT_NOT_FOUND.toLowerCase();
    public static final String ERR_INVALID_PRODUCT_PRICE = "Недопустимая цена товара";
    public static final String ERR_MUST_HAVE_TITLE = "Товар не может не иметь названия";
    public static final String ERR_MUST_HAVE_NAME = "Пользователь не может не иметь имени";
    public static final String ERR_MUST_HAVE_ROLE = "Зарегистрированный пользователь должен иметь права";
    public static final String ERR_WRONG_AUTH = "Недопустимое имя пользователя или пароль";
    public static final String ERR_TOKEN_EXPIRED = "Срок действия токена истек";
}