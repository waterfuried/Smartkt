package ru.geekbrains.smartkt.util;

import ru.geekbrains.smartkt.dto.OrderedProductDTO;
import ru.geekbrains.smartkt.dto.ProductDTO;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.*;

@Component
public class Cart {
    private List<OrderedProductDTO> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
    }

    public List<OrderedProductDTO> getProducts() { return products; }

    public void add(ProductDTO product) {
        if (products.size() > 0)
            for (OrderedProductDTO op : products)
                if (Objects.equals(op.getProductId(), product.getId())) {
                    op.incQuantity();
                    return;
                }
        products.add(new OrderedProductDTO(products.size()+1, product.getId(), 1));
    }

    public void delete(Integer id) {
        if (products.size() > 0)
            products.removeIf(op -> Objects.equals(op.getProductId(), id));
    }
}