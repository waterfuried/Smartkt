package ru.geekbrains.smartkt.dao.orders;

import javax.persistence.*;

import lombok.*;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

// состояние заказа: 1=принят, 2=формируется, 3=готов к выдаче, 4=получен
@Entity
@Table(name="order_statuses")
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private int status;

    public OrderStatus() { status = ORDER_STATUS_REGISTERED; }
}