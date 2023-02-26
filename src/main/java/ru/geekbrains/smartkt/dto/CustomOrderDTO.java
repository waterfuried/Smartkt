package ru.geekbrains.smartkt.dto;

import lombok.Data;

import java.util.*;

import ru.geekbrains.smartkt.dao.delivery.Delivery;
import ru.geekbrains.smartkt.dao.users.Customer;
import static ru.geekbrains.smartkt.prefs.Prefs.ORDER_STATUS_REGISTERED;

@Data
public class CustomOrderDTO {
    private Integer id;
    private String partNumber;
    private Integer status;
    private Customer customer;
    private Delivery delivery;
    private List<OrderedItemDTO> items;

    public CustomOrderDTO() {
        status = ORDER_STATUS_REGISTERED;
        items = new ArrayList<>();
    }
}