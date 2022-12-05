package ru.geekbrains.smartkt.dao.items;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

// товар на складе
@Entity
@Table(name = "item_storage")
@Data
@RequiredArgsConstructor
public class StoredItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // регистрационный номер (обозначение)
    @Column(name = "part_number")
    private String partNumber;

    // связь с таблицей товаров,
    // многие-к-одному: товар на складе соответствует какому-либо товару из их множества
    @ManyToOne
    @JoinColumn(name = "id")
    private Item item;

    // количество
    @Column(name = "amount")
    private Integer amount;

    // время регистрации товара на складе
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификации зарегистрованного товара
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}