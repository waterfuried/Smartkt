package ru.geekbrains.smartkt.dao.users;

import ru.geekbrains.smartkt.dto.UserDTO;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;
import java.util.*;

import lombok.*;

// В базе данных необходимо реализовать возможность хранить информацию о покупателях (id, имя)
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

    // гостевые (неавторизованные) пользователи смогут посмотреть наличие
    // интересующего их товара и забрать его самовывозом;
    // в БД же хранятся только зарегистрированные

    // имя
    @Column(name = "name")
    // из-за использования в репозитории CustomerRepository
    // интерфейса JpaRepository имя поля должно быть именно таким
    private String username;

    // пароль
    @Column(name = "password")
    private String password;

	// и почта, и адрес, и телефон - поля необязательные для заполнения,
	// однако, если пользователь захочет получить товар доставкой,
	// какое-либо из них ему придется указать
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    // связь с таблицей ролей,
    // многие-ко-многим
    @ManyToMany
    @JoinTable(name = "users_roles",
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
        username = user.getName();
        email = user.getEmail();
        roles = new ArrayList<>();
        for (String role : user.getRoles().split(","))
            roles.add(new Role(role.trim()));
    }

    @Override
    // поскольку в CustomOrder есть поле Customer, вывод заказов покупателя здесь
    // приведет к перекрестным вызовам и, в итоге, к переполнению стека
    public String toString() { return "Customer (id = "+id+", name = '"+username+"')"; }
}