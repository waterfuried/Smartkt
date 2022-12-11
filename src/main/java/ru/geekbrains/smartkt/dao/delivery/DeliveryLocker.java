package ru.geekbrains.smartkt.dao.delivery;

import javax.persistence.*;

import lombok.*;

// постамат
@Entity
@Table(name="delivery_lockers")
@Data
@RequiredArgsConstructor
public class DeliveryLocker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // адрес
    @Column(name = "location")
    private String location;
}