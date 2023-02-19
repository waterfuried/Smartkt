package ru.geekbrains.smartkt.dao.users;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

import lombok.*;

import ru.geekbrains.smartkt.dto.UserDTO;

/*
    В базе данных необходимо реализовать возможность хранить информацию о покупателях (id, имя)

    1. имена полей определяют названия методов репозитория CustomerRepository,
    поскольку он реализует интерфейс JpaRepository:
    например, метод, производящий поиск по имени, может называться findByName,
    но если поле называлось бы Username, то метод - findByUsername

    2. гостевые (неавторизованные) пользователи смогут посмотреть наличие
    интересующего их товара и забрать его самовывозом;
    в БД же хранятся только зарегистрированные

    3. и почта, и адрес, и телефон - поля необязательные для заполнения,
    однако, если пользователь захочет получить товар доставкой,
    какое-либо из них ему придется указать
*/
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username") // имя
    private String name;

    @Column(name = "password") // пароль
    private String password;

    @Column(name = "email")  // почта
    private String email;

    @Column(name = "address") // адрес
    private String address;

    @Column(name = "phone") // телефон
    private String phone;

    // связь с таблицей ролей,
    // многие-ко-многим
    @ManyToMany
    @JoinTable(name = "ur_links",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    // время регистрации
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время изменения регистрации
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Customer(UserDTO user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        address = user.getAddress();
        phone = user.getPhone();
        roles = new ArrayList<>();
        for (String role : user.getRoles().split(","))
            roles.add(new Role(role.trim()));
    }

    @Override
    // поскольку в CustomOrder есть поле Customer, вывод заказов покупателя здесь
    // приведет к перекрестным вызовам и, в итоге, к переполнению стека
    public String toString() { return "Customer (id = "+id+", name = '"+name+"')"; }
}