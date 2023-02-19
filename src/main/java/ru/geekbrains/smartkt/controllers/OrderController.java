package ru.geekbrains.smartkt.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.*;

import ru.geekbrains.smartkt.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    // TODO: для оформления заказа (в зависимости от выбранного способа доставки -
    //  самовывоз из пункта выдачи/доставка в постамат/доставка курьером покупателю -
    //  может потребоваться получение списка адресов пунктов выдачи/постаматов,
    //  а в случае доставки курьером - адрес покупателя (если он есть, то в форме
    //  заказа отображается по умолчанию, если нет, то поле "адрес доставки"
    //  ОБЯЗАТЕЛЬНО должно быть заполнено при оформлении)
    //  ? эти списки, скорее всего, нужно получать здесь, но как - отдельными запросами ?
    private final OrderService service;

    @PostMapping
    // TODO: front. create order -> http.post(customer, cart, delivery)
    public void createOrder(/*@RequestHeader String username*/) {
        //service.createOrder(customer, cart, delivery);
    }

    // после оформления заказа он может быть отменен, по разным причинам, например,
    // - покупатель сделал схожий заказ в другом магазине и уже получил его,
    // - при комплектации выяснилось, что на складе нет части (или всех) заказанных товаров
    // TODO: реализовать отмену заказа - по кнопке в списке заказов покупателя
    @DeleteMapping("/del/{id}")
    public void cancel(@PathVariable Integer id) { service.cancel(id); }

    // список всех заказов покупателя
    /*@GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTOShort> getAllOrders(@RequestHeader String username) {
        if (username == null) return new ArrayList<>();
        List<Order> orderPerUser = orderRepository.findAllByUserName(username);
        List<OrderDTOShort> ordersDTO = new ArrayList<>();
        orderPerUser.forEach(order -> ordersDTO.add(new OrderDTOShort().createOrderDTO(order)));
        return ordersDTO;
    }

    // определенный заказ покупателя
    @PostMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTOFullInfo getOneOrder(@RequestHeader String username, @PathVariable Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent() && username.equals(order.get().getUserName())) {
            return DTOConverter.createOrderDTOFullInfoFromOrder(order.get());
        }
        return null;
    }*/
}