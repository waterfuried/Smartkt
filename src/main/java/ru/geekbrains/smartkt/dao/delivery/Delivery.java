package ru.geekbrains.smartkt.dao.delivery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.sql.Timestamp;

import lombok.*;

// доставка (получение) заказов
@Entity
@Table(name="delivery")
@Data
@RequiredArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // регистрационный номер доставки (при получении самовывозом отсутствует)
    @Column(name = "part_number")
    private String partNumber;

    // тип доставки:
    // 0 = самовывоз из пункта выдачи
    // 1 = доставка в постамат
    // 2 = доставка курьером/службой доставки
    @Column(name = "type")
    private Integer type;

	// указатель на место (код места) доставки, при типах доставки 0 и 1:
    // 0 = код пункта выдачи,
	// 1 = код постамата
	// связь здесь не задана, поскольку нужная таблица - delivery_points или delivery_lockers -
    // определяется программно, в зависимости от значения типа доставки
    @Column(name = "dest_code")
    private Integer destCode;

    // адрес доставки, если она производится курьером/службой доставки
	// может совпадать с адресом покупателя,
	// но может быть им же указан каким-либо другим
    @Column(name = "dest_addr")
    private String destAddress;

	// дата и время начала выполнения доставки (регистрация заявки на доставку)
    @Column(name = "started_at")
    private Timestamp startedAt;

    // предельный срок доставки, дней
    @Column(name = "deadline")
    private Integer deadline;

    // стоимость доставки
    @Column(name = "price")
    private Integer price;

    // время регистрации заявки на доставку
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    // время изменения зарегистрованной заявки
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}