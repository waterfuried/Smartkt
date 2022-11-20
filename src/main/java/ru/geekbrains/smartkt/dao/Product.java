/*
    DAO - Data Access Object - абстрактный интерфейс к какому-либо типу БД
    или механизму хранения. Традиционно этот шаблон проектрования связывают
    с приложениями на платформе Java EE, взаимодействующими с реляционными БД
    через интерфейс JDBC
*/
package ru.geekbrains.smartkt.dao;

import ru.geekbrains.smartkt.dto.ProductDTO;

import javax.persistence.*;

import lombok.*;

import java.util.*;

@Entity
@Table(name = "products")
@Data
// создать набор конструкторов с 1 параметром для каждого из полей, а также конструктор без параметров
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private Integer cost;

    // связь с таблицей заказов (один-ко-многим, продукт-в-заказах) устанавливается
    // с классом сущности по его полю, а не с соответствующей ему таблицей БД по столбцу
    // при наличии связанных полей в других сущностях каскадные операции (например, удаление)
    // удалят как объект сущности, так и записи в соответствующих сущностям таблицах
    // для этого в аннотации нужно указать (через запятую) cascade = CascadeType.REMOVE
    @OneToMany(mappedBy = "product")
    private List<CustomOrder> orders;

    public Product(Integer id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Product(ProductDTO product) {
        id = product.getId();
        title = product.getTitle();
        cost = product.getCost();
    }

    @Override
    public String toString() {
        return "Product (id= "+id+", title='" +title+"', cost="+cost+")";
    }
}