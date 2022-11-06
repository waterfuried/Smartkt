package ru.geekbrains.smartkt.dao;

import javax.persistence.*;

import lombok.*;

import java.sql.Timestamp;

// класс заказа (покупатель делает заказ продуктов):
// - номер заказа
// - номер товара в заказе (внешний ключ)
// - номер покупателя (внешний ключ)
// - дата и время создания заказа
// - дата и время оплаты/получения заказа
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne // связь с таблицей продуктов (многие-к-одному, в разных заказах может быть один и тот же продукт)
    //такое имя для этого поля будет использоваться Hibernate по умолчанию
    //если нужно другое, его следует указать явно
    //@JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne // связь с таблицей покупателей (многие-к-одному, покупатели-заказ)
    //@JoinColumn(name = "customer_id")
    private Customer customer;

    // дата и время добавления продукта в заказ (НЕ может отсутствовать)
    @Column(name = "date_time")
    private Timestamp date_time;

    // дата и время оплаты (получения) заказа (может отсутствовать)
    @Column(name = "paid")
    private Timestamp paid;

    @Override
    public String toString() {
        return "Order:"+
		"\n\tid="+id+
		"\n\tcustomer="+customer+
		"\n\tproduct="+product+
		"\n\tadded="+date_time+
        (paid == null ? "" : "\n\tpaid="+paid);
    }
}