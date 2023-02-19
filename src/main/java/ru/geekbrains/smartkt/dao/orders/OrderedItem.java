package ru.geekbrains.smartkt.dao.orders;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

import ru.geekbrains.smartkt.dao.items.StoredItem;
import ru.geekbrains.smartkt.dto.OrderedItemDTO;

@Entity
@Table(name = "ordered_items")
@Data
@RequiredArgsConstructor
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // связь с таблицей заказов
    // многие-к-одному: в один заказ могут входить разные заказанные товары
    // (или - многие заказы могут ссылаться на этот заказанный товар)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomOrder order;

    // связь с таблицей товаров
    // многие-к-одному: заказанный товар может быть одним из их множества на складе
    @ManyToOne
    @JoinColumn(name = "item_id")
    private StoredItem item;

    // количество
    @Column(name = "amount")
    private Integer amount;

    // общая стоимость всего количества заказанного товара - вычисляемая
    // TODO: при расчете стоимости товара может потребоваться
    //  проверка наличия акций/скидок по нему - например,
    //  - "2 по цене одного",
    //  - или "скидка x% при наличии карты покупателя/потоянного клиента",
    //  - или "цена товара - K, при покупке от M кг, иначе - N",
    //  кроме того, скидка по одному товару может зависеть от наличия в заказе другого,
    //  поэтому не ясно, каким образом их учитывать и реализовывать,
    //  тем не менее, это ВАЖНО, поскольку заказанный товар входит в корзину,
    //  общую стоимость которой необходимо пересчитвыать при КАЖДОМ добавлении/удалении товаров
    @Column(name = "total_cost")
    private Integer totalCost;

    // время добавления в заказ
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификации товара в заказе
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderedItem(OrderedItemDTO item) {
        id = item.getId();
        //order = item.getOrderId();
        //this.item = ?
        amount = item.getQuantity();
        totalCost = -1;
    }
}