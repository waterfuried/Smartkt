package ru.geekbrains.smartkt.dto;

import ru.geekbrains.smartkt.dao.users.*;
import ru.geekbrains.smartkt.exceptions.ValidationException;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name, password, email, roles;
    private LocalDateTime createdAt, updatedAt;

    public UserDTO(Customer c) {
        UserDTO dto = new UserDTO();
        dto.setId(c.getId());
        dto.setName(c.getUsername());
        dto.setPassword(c.getPassword());
        dto.setEmail(c.getEmail());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setUpdatedAt(c.getUpdatedAt());

        List<Role> roles = c.getRoles();
        StringBuilder sb = new StringBuilder();
        roles.forEach(role -> sb.append(role.getType()).append(", "));
        if (sb.length() > 0) dto.setRoles(sb.substring(0, sb.length()-2));
    }

    public void validate() {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) errors.add(ERR_MUST_HAVE_NAME);
        if (roles == null || roles.isBlank()) errors.add(ERR_MUST_HAVE_ROLE);

        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}