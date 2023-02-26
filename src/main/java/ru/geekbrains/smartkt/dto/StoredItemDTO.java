/*
    DTO - Data Transfer Object - шаблон проектирования,
    используется для передачи данных между подсистемами приложения.
    В Enterprise JavaBeans используется для сериализации
*/
package ru.geekbrains.smartkt.dto;

import java.util.*;

import lombok.*;

import ru.geekbrains.smartkt.dao.items.*;
import ru.geekbrains.smartkt.exceptions.*;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

// контракт по виду товара на складе
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredItemDTO {
    private Integer id;
    private String title;
    private ItemDescription description;
    private ItemProvider provider;
    private Integer cost, amount;

    public StoredItemDTO(StoredItem item) {
        id = item.getId();
        title = item.getTitle();
        cost = item.getCost();
        amount = item.getAmount();
        provider = item.getProvider();
        description = item.getDescription();
    }

    public void validate() {
        List<String> errors = new ArrayList<>();

        if (cost < 1) errors.add(ERR_INVALID_PRODUCT_PRICE+": "+cost);
        if (amount < 1) errors.add(ERR_INVALID_PRODUCT_AMOUNT+": "+amount);
        if (title.isBlank()) errors.add(String.format(ERR_MUST_HAVE_TITLE, ITEM_NAME));
        if (provider.getTitle().isBlank()) errors.add(String.format(ERR_MUST_HAVE_TITLE, PROVIDER_NAME));

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}