package ru.geekbrains.smartkt.services;

import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;

import lombok.*;

@Service
@RequiredArgsConstructor
// 3. * Создаете сервис, позволяющий по id покупателя узнать список купленных им товаров,
// и по id товара узнавать список покупателей этого товара;
public class OrderService {
    /*private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    @Autowired
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
    }

    public List<Order> getUserOrders(Customer customer) {
        return repository.findAllByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }*/
}