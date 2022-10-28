/*
    DAO - Data Access Object - абстрактный интерфейс к какому-либо типу БД
    или механизму хранения. Традиционно этот шаблон проектрования связывают
    с приложениями на платформе Java EE, взаимодействующими с реляционными БД
    через интерфейс JDBC
*/
package ru.geekbrains.smartkt.dao;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private Integer cost;

    @ManyToOne // связь с таблицей заказов (многие-к-одному, продукты-в-заказе)
    //@JoinColumn(name="order_id")
    private Order order;

    @Override
    public String toString() {
        return "Product:"+
		"\n\tid="+id+
		"\n\ttitle='" +title+'\''+
		"\n\tcost="+cost;
    }
}