package ru.geekbrains.smartkt.util;

import ru.geekbrains.smartkt.dto.OrderedProductDTO;
import ru.geekbrains.smartkt.dto.ProductDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Cart {
    private List<OrderedProductDTO> products;

    @PostConstruct
    public void init() { products = new ArrayList<>(); }

    public List<OrderedProductDTO> getProducts() { return products; }

    // добавление товара в корзину
    public void add(ProductDTO product) {
        if (products.size() == 0) return;
        int i = 0;
        while (i < products.size() && !products.get(i).getProductId().equals(product.getId())) i++;
        if (i < products.size())
            products.get(i).incQuantity();
        else
            products.add(new OrderedProductDTO(products.size()+1, product.getId(), 1));
    }

    // удаление из корзины
    public void delete(Integer id) {
        if (products.size() == 0 || id >= products.size() || products.get(id) == null) {
            log.error(ERR_PRODUCT_NOT_FOUND + ": id=" + id);
            throw new ResourceNotFoundException(ERR_PRODUCT_NOT_FOUND);
        }
        products.remove((int)id);
    }

    // очистка корзины
    public void clear() { products.clear(); }
}