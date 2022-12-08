package ru.geekbrains.smartkt.dao.items;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

// производитель/поставщик товара
@Entity
@Table(name = "item_providers")
@Data
@RequiredArgsConstructor
public class ItemProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // наименование
    @Column(name = "title")
    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}