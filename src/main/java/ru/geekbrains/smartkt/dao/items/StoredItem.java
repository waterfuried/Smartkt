package ru.geekbrains.smartkt.dao.items;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import ru.geekbrains.smartkt.dto.ProductDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

/*
    товар на складе является основой

    именно со склада производится формирование заказов по заявкам потребителей:
    список доступных для заказов ими товаров содержит все то их множество, которое имеется на складе;
    как товар на него попадает - вопрос за рамками концепции
*/
@Slf4j
@Entity
@Table(name = "item_storage")
@Data
// создать конструкторы с 1 параметром для всех FINAL-полей и/или для помеченных аннотацией @NonNull
@RequiredArgsConstructor
public class StoredItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // регистрационный номер (обозначение)
    // если тип данных таблицы БД не определен ранее, его можно указать здесь -
    // columnDefinition = "varchar(200) default '00000000'"
    @Column(name = "part_number")
    private String partNumber;

    // наименование
    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private Integer cost;

    // количество
    @Column(name = "amount")
    private Integer amount;

    /* о связях:
            1. связь устанавливается с классом сущности по его полю,
               а не с соответствующей ему таблицей БД по столбцу
            2. при наличии связанных полей в других сущностях каскадные операции (например, удаление)
               удалят как объект сущности, так и записи в соответствующих сущностям таблицах
               для этого в аннотации нужно указать (через запятую) cascade = CascadeType.REMOVE
    */

    // связь с таблицей производителей/поставщиков
    // многие-к-одному: любой товар производится/поставляется каким-либо одним юр.лицом из их множества
    @ManyToOne
    @JoinColumn(name = "provider_id") // имя связующего столбца в этой таблице БД
    private ItemProvider provider;

    // связь с таблицей описаний товаров
    @ManyToOne
    @JoinColumn(name = "desc_id")
    private ItemDescription description;

    // связь с таблицей изображений товаров
    @OneToMany(mappedBy = "item")   // имя связующего поля в классе
    private List<ItemImage> images;

    // время регистрации товара на складе
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификации зарегистрованного товара
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public StoredItem(Integer id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public StoredItem(ProductDTO product) {
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