package ru.geekbrains.smartkt.dao.orders;

import ru.geekbrains.smartkt.dao.users.Customer;
import ru.geekbrains.smartkt.dao.delivery.Delivery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

import lombok.*;

/*
    заказ (покупатель заказывает товары)
    - переделан: поскольку номер заказа является первичным ключом,
                 с прошлым подходом в один заказ невозможно было добавить более одного типа товара
    - переименован (из Order): во избежание проблем с зарезервированным в SQL словом "order"
*/
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // регистрационное обозначение (номер) заказа для отчетности,
	// предполагается, что может не указываться,
	// в случае же указаний такие обозначения стоило бы свести в отдельную таблицу,
	// а здесь размещать ссылку на соответствующую запись в ней
    @Column(name = "part_number")
    private String partNumber;

    // связь с таблицей состояний заказов
    // многие-к-одному: заказ может находиться в одном состоянии из их множества
    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    // связь с таблицей покупателей
    // многие-к-одному: покупатели-заказ
    // списки заказов покупателей можно определить запросом
    // select customers.name, orders.id from orders inner join customers on customers.id = orders.customer_id
    @ManyToOne
    /*
       похоже, что такое имя для этого столбца будет использоваться Hibernate по умолчанию;
       если нужно другое, его следует указать явно
    */
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // связь с таблицей доставок
    // один-к-одному: заказ может быть получен каким-либо одним выбранным способом
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // дата и время создания заказа
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // дата и время оплаты (получения) заказа
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // список товаров в заказе
    @OneToMany(mappedBy = "order")
    private List<OrderedItem> items;

    @Override
    public String toString() {
        return "Order:"+
		"\n\tid="+id+
        (partNumber == null ? "" : "\n\tPN="+partNumber)+
		"\n\tcustomer="+customer+
        "\n\tstatus="+status+
		"\n\tdelivery="+delivery+
		"\n\tadded="+createdAt+
        (updatedAt == null ? "" : "\n\tpaid="+updatedAt);
    }
}