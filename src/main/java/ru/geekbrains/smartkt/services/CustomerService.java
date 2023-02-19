package ru.geekbrains.smartkt.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.*;
import java.util.stream.*;

import lombok.*;

import ru.geekbrains.smartkt.dao.users.*;
import ru.geekbrains.smartkt.dto.UserDTO;
import ru.geekbrains.smartkt.repositories.*;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

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

    private Optional<Customer> findByName(String name) { return repository.findByName(name); }
    private Optional<Customer> findById(Integer id) { return repository.findById(id); }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Customer c = findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(ERR_USER_NOT_FOUND+": "+name));
        return new User(c.getName(), c.getPassword(), mapRolesToAuthorities(c.getRoles()));
    }

    public UserDTO loadUserById(Integer id) throws UsernameNotFoundException {
        Customer c = findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(ERR_USER_NOT_FOUND+": id="+id));
        return new UserDTO(c);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getDesignation())).collect(Collectors.toList());
    }

    public List<UserDTO> getUsersList() {
        List<Customer> users = repository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public Customer addOrUpdate(Customer c) { return repository.save(c); }

    public void delete(Integer id) { repository.deleteById(id); }
}