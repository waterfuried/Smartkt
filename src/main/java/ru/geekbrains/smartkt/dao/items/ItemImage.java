package ru.geekbrains.smartkt.dao.items;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

// изображение товара
@Entity
@Table(name = "item_images")
@Data
@RequiredArgsConstructor
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // связь с таблицей товаров на складе
    @ManyToOne
    @JoinColumn(name = "item_id")
    private StoredItem item;

    @Column(name="path")
    private String path;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}