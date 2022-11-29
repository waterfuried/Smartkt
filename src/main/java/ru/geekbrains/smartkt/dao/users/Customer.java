package ru.geekbrains.smartkt.dao.users;

import ru.geekbrains.smartkt.dao.CustomOrder;
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
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    // из-за использования в репозитории CustomerRepository
    // интерфейса JpaRepository имя поля должно быть именно таким
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    // связь с таблицей ролей
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //списки заказов покупателей можно определить запросом
    //select customers.name, orders.id from orders inner join customers on customers.id = orders.customer_id
    @OneToMany(mappedBy= "customer") // связь с таблицей заказов (один-к-многим, покупатель-заказы)
    private List<CustomOrder> orders;

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