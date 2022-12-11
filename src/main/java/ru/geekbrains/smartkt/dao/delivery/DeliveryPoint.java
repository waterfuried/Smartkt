package ru.geekbrains.smartkt.dao.delivery;

import javax.persistence.*;

import lombok.*;

// пункт выдачи заказов
@Entity
@Table(name="delivery_points")
@Data
@RequiredArgsConstructor
public class DeliveryPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // адрес
    @Column(name = "location")
    private String location;
}