package ru.geekbrains.smartkt.dao.orders;

import ru.geekbrains.smartkt.dao.items.StoredItem;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

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
    @JoinColumn(name = "id")
    private CustomOrder order;

    // связь с таблицей товаров
    // многие-к-одному: заказанный товар может быть одним из их множества на складе
    @ManyToOne
    @JoinColumn(name = "id")
    private StoredItem item;

    // количество
    @Column(name = "amount")
    private Integer amount;

    // время добавления в заказ
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификации товара в заказе
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}