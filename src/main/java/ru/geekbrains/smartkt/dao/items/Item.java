/*
    DAO - Data Access Object - абстрактный интерфейс к какому-либо типу БД
    или механизму хранения. Традиционно этот шаблон проектрования связывают
    с приложениями на платформе Java EE, взаимодействующими с реляционными БД
    через интерфейс JDBC
*/
package ru.geekbrains.smartkt.dao.items;

import ru.geekbrains.smartkt.dto.ProductDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

// товар
@Entity
@Table(name = "items")
@Data
@Slf4j
// создать конструкторы с 1 параметром для всех FINAL-полей и/или для помеченных аннотацией @NonNull
@RequiredArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // наименование
    @Column(name = "title")
    private String title;

    /* о связях:
            1. связь устанавливается с классом сущности по его полю,
               а не с соответствующей ему таблицей БД по столбцу
            2. при наличии связанных полей в других сущностях каскадные операции (например, удаление)
               удалят как объект сущности, так и записи в соответствующих сущностям таблицах
               для этого в аннотации нужно указать (через запятую) cascade = CascadeType.REMOVE
    */

    // связь с таблицей производителей/поставщиков
    @OneToMany(mappedBy = "id")
    @Column(name = "provider_id")
    private List<ItemProvider> providers;

    // TODO(?): поскольку у производителей/поставщиков цены и количества товаров могут быть разными,
    //  наверно, следующие 2 поля также нужно делать списками
    // цена у производителя/поставщика
    @Column(name = "cost")
    private Integer cost;

    // кол-во у производителя/поставщика
    @Column(name = "amount")
    private Integer amount;

    // связь с таблицей описаний товаров
    @ManyToOne
    @JoinColumn(name = "id")
    private ItemDescription description;

    // связь с таблицей изображений товаров
    @OneToMany(mappedBy = "item")
    private List<ItemImage> images;

    // время создания
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификации
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Item(Integer id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Item(ProductDTO product) {
        id = product.getId();
        title = product.getTitle();
        cost = product.getCost();
    }

    @Override
    public String toString() { return "Item (id= "+id+", title='" +title+"', cost="+cost+")"; }

    // добавление и удаление изображений товара
    public void addImage(ItemImage img) {
        if (images == null) images = new ArrayList<>();
        images.add(img);
    }

    public void delImage(Integer id) {
        if (images == null || images.size() == 0 || id >= images.size() || images.get(id) == null)  {
            String s = String.format(ERR_NOT_FOUND, "Изображение", "о");
            log.error(s + ": id=" + id);
            throw new ResourceNotFoundException(s);
        }
        images.remove((int)id);
    }

    public void clearImages() { images.clear(); }
}