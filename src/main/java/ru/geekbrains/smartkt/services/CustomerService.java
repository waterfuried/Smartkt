package ru.geekbrains.smartkt.services;

import ru.geekbrains.smartkt.dao.users.*;
import ru.geekbrains.smartkt.dto.UserDTO;
import ru.geekbrains.smartkt.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.*;
import java.util.stream.*;

import lombok.*;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerRepository repository;

    /*@Autowired
    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer find(String name) throws RuntimeException { return repository.find(name); }

    public void add(Customer customer) { repository.add(customer); }

    public void delete(Integer id) { repository.delete(id); }*/

    public Optional<Customer> find(String name) { return repository.findByUsername(name); }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer c = find(name)
                .orElseThrow(() -> new UsernameNotFoundException("User '"+name+"' not found"));
        return new User(c.getUsername(), c.getPassword(), mapRolesToAuthorities(c.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getType())).collect(Collectors.toList());
    }

    public List<UserDTO> getUsersList() {
        List<Customer> users = repository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public Customer add(Customer c) { return repository.save(c); }
}