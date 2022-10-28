package ru.geekbrains.smartkt.dao;

import javax.persistence.*;

import lombok.*;

import java.sql.Timestamp;

// класс заказа (покупатель делает заказ продуктов):
// - номер заказа
// - список товаров в заказе (внешний ключ)
// - покупатель customer (OneToMany)
// - дата и время заказа
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

    @ManyToOne // связь с таблицей продуктов (один-ко-многим, заказ-продукты)
    //такое имя для этого поля будет использоваться Hibernate по умолчанию
    //если нужно другое, его следует указать явно
    //@JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne // связь с таблицей покупателей (многие-к-одному, покупатели-заказ)
    //@JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date_time")
    private Timestamp date_time;

    @Override
    public String toString() {
        return "Order:"+
		"\n\tid="+id+
		"\n\tcustomer="+customer+
		"\n\tproduct="+product+
		"\n\ttimestamp="+date_time;
    }
}