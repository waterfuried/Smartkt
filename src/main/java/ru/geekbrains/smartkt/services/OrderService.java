package ru.geekbrains.smartkt.services;

import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import lombok.*;

import ru.geekbrains.smartkt.dao.delivery.Delivery;
import ru.geekbrains.smartkt.dao.orders.CustomOrder;
import ru.geekbrains.smartkt.dao.users.Customer;
import ru.geekbrains.smartkt.dto.CustomOrderDTO;
import ru.geekbrains.smartkt.dto.OrderedItemDTO;
import ru.geekbrains.smartkt.util.Cart;

import ru.geekbrains.smartkt.repositories.OrderRepository;
import ru.geekbrains.smartkt.repositories.ProductRepository;

import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

@Service
@RequiredArgsConstructor
// * Создаете сервис, позволяющий по id покупателя узнать список купленных им товаров,
// и по id товара узнавать список покупателей этого товара;
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    /*@Autowired
    public void setOrderService(OrderService service) {
        orderService = service;
    }

    @Autowired
    public void setOrderRepository(OrderRepository repository) {
        this.orderRepository = repository;
    }

    public OrderedProduct(Integer productId, Integer orderId) {
        Product p;
        Order order;
        try {
            p = productRepository.find(productId);
            order = orderRepository.find(orderId);
            OrderedProduct op = new OrderedProduct();
            op.setProduct(p);
            op.setOrder(order);
            return op;
        } catch (RuntimeException ex) { return null; }
    }

    public Order getOrderById(Integer id) {
        return orderRepository.find(id);
    }*/

    // получить список всех заказов покупателя
    public List<CustomOrderDTO> getAllOrders(Customer customer) {
        return orderRepository
                .findAllOrdersByCustomerId(customer.getId())
                .stream()
                .map(p -> new CustomOrderDTO()).collect(Collectors.toList());
    }

    // получить список товаров в заказе покупателя
    public List<OrderedItemDTO> getOrderContent(Customer customer, CustomOrderDTO order) {
        for (CustomOrderDTO c : getAllOrders(customer))
            if (c.getId().equals(order.getId())) return c.getItems();

        throw new ResourceNotFoundException(ERR_ORDER_NOT_FOUND, "id="+order.getId());
    }

    // отменить заказ
    public void cancel(Integer id) { orderRepository.deleteById(id); }

    // создать заказ покупателя
    // 2.1. Реализовать сохранение покупок, которые пользователь добавил в корзину,
    //      в виде заказов, сохраняемых в БД.
    public CustomOrderDTO createOrder(Customer customer, Cart cart, Delivery delivery) {
        CustomOrderDTO order = new CustomOrderDTO();
        order.setCustomer(customer);
        order.setDelivery(delivery);
        order.setItems(cart.getContent());
        // TODO:
        //  1) сам заказ и заказанные товары (каскадная операция)
        //     должны сохраняться в соответствующих таблицах БД
        //  2) после оформления заказа на складе уменьшить кол-во соответствующего товара
        orderRepository.save(new CustomOrder(order));
        return order;
    }

    // общая стоимость товара в заказе (TODO: с учетом скидок)
    public int getTotalItemCost(OrderedItemDTO item) {
        return item.getTotalCost() < 0
                ? item.getQuantity() * item.getCost()
                : item.getTotalCost();
    }

    public OrderedItemDTO setTotalItemCost(OrderedItemDTO item) {
        OrderedItemDTO newItem = item.toBuilder().build();
        newItem.setTotalCost(getTotalItemCost(item));
        return newItem;
    }

    // общая стоимость заказа (TODO: с учетом скидок)
    public int getTotalCost(Customer customer, CustomOrderDTO order) {
        int total = 0;
        for (OrderedItemDTO item : getOrderContent(customer, order)) total += getTotalItemCost(item);
        return total;
    }

    // следующие 2 метода, могут потребоваться только в исключительных случаях:
    // в норме предполагается, что при оформлении заказа все товары из корзины
    // должны переходить в заказ, а после этого операции по добавлению/удалению
    // из корзины пользователю должны становиться недоступными.
    // однако, если выяснится, что какой-либо товар на складе закончился -
    // в списке товаров на складе было указано какое-то старое его количество, -
    // тогда может потребоваться возможность правки заказа -
    // удаление товара с (или без) заменой на другой,
    // или изменение его количества (в сторону уменьшения)

    // добавить заказанный товар в заказ
    public CustomOrderDTO addItem(CustomOrderDTO order, OrderedItemDTO item) {
        order.getItems().add(item);
        return order;
    }

    // исключить товар из заказа
    public CustomOrderDTO removeItem(CustomOrderDTO order, OrderedItemDTO item) {
        if (!order.getItems().removeIf(p -> p.getProductId().equals(item.getId())))
            throw new ResourceNotFoundException(ERR_NO_SUCH_ITEM_IN_ORDER+
                    ": id "+ITEM_NAME+"а="+item.getId()+
                    ", id "+ORDER_NAME+"а="+order.getId(), null);
        return order;
    }

    /*public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }*/
}