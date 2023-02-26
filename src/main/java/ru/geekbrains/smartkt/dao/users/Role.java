package ru.geekbrains.smartkt.dao.users;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import java.time.*;

import lombok.*;

// роль пользователя
@Entity
@Table(name = "roles")
@Data
// конструктор по умолчанию (без аргументов) должен быть всегда -
// если есть поля с аннотацией NonNull и используется аннотация RequiredArgsConstructor,
// аннотацию NoArgsConstructor нужно тоже использовать
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // наименование роли: гость, пользователь, менеджер, администратор
    @Column(name = "designation")
    @NonNull
    private String designation;

    // время добавления
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // время модификции
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}