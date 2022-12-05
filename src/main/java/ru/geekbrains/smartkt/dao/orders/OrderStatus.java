package ru.geekbrains.smartkt.dao.orders;

import javax.persistence.*;

import lombok.*;

// состояние заказа: принят, формируется, готов к выдаче, получен
@Entity
@Table(name="order_statuses")
@Data
@RequiredArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String status;
}