package ru.geekbrains.smartkt.util;

import org.springframework.web.context.annotation.SessionScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.*;

import lombok.*;

import ru.geekbrains.smartkt.dto.OrderedItemDTO;
import ru.geekbrains.smartkt.dto.StoredItemDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import ru.geekbrains.smartkt.services.ProductService;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

/*
    корзина предназначена для хранения добавляемых в нее товаров (всегда со склада);
    поскольку id товара на складе однозначно отпределяет сам товар,
    методам достаточно передавать только id товара, а не его DTO

    после оформления заказа, он сохраняется в БД - хранить в БД корзины нет никакой нужды;
    если покупатель сделал несколько заказов, он сформировал разные корзины,
    пусть даже существующие одновременно - до момента их получения им.

    какую в этом случае корзину показывать покупателю при следующем его входе в магазин?
    в момент входа покупателя из БД нужно считывать список его заказов,
    а в нем проверять наличие действующих, и
    - если такой заказ лишь один, под него выделяется (создается) корзина,
      в которую вкладывается содержимое заказа и эта корзина отображается;
    - если заказов несколько, по кнопке/ссылке "Мои заказы" нужно отображать список заказов -
      в этом списке для каждого элемента существует кнопка/ссылка "Показать", по нажатию которой
      выполняются те же действия, что и в случае одного действующего заказа (см. предыдущий абзац)
*/
// 2.2. * Подумать, возможно ли корзину реализовать через сессионный бин.
//        Если возможно и целесообразно, то реализовать это в коде.
@Component
@SessionScope
@RequiredArgsConstructor
public class Cart {
    private List<OrderedItemDTO> items;
    private final ProductService storageService;

    @PostConstruct
    public void init() { items = new ArrayList<>(); }

    // получение (финального) содержимого корзины - возвращаемый список никак изменить невозможно
    public List<OrderedItemDTO> getContent() { return Collections.unmodifiableList(items); }

    // найти товар в корзине
    // товар, выбранный из списка товаров на складе, точно на нем присутствует
    public int find(Integer id) {
        int i = 0;
        while (i < items.size() && !items.get(i).getProductId().equals(storageService.getOne(id).getId())) i++;
        return i < items.size() ? i : -1;
    }

    // добавить товар в корзину
    // TODO: нужно ли возвращать новый/измененный заказанный товар?
    public void add(Integer id) {
        int i = find(id);
        if (i < 0) {
            StoredItemDTO item = storageService.getOne(id);
            items.add(new OrderedItemDTO(
                    items.size() + 1, id, item.getCost(), 1, -1, item.getTitle())
            );
        } else
            items.get(i).incQuantity(1);
    }

    // увеличить количество товара в корзине
    private void incAmount(Integer id, int amount) {
        int i = find(id);
        if (i >= 0) items.get(i).incQuantity(amount);
    }

    // уменьшить количество товара в корзине или удалить его
    private void decAmount(Integer id, int amount) {
        int i = find(id);
        if (i >= 0)
            if (amount >= items.get(i).getQuantity())
                items.remove(i);
            else
                items.get(i).decQuantity(amount);
    }

    public void changeAmount(Integer id, int amount) {
        if (amount != 0)
            if (amount > 0) incAmount(id, amount); else decAmount(id, -1*amount);
    }

    // удалить из корзины товар
    // TODO: проверить, произойдет ли перехват выбрасывания глобальным обработчиком исключений
    public void delete(Integer id) {
        if (!items.removeIf(item -> item.getId().equals(id)))
            throw new ResourceNotFoundException(ERR_ITEM_NOT_FOUND, "id="+id);
    }

    // очистить корзину
    public void clear() { items.clear(); }
}