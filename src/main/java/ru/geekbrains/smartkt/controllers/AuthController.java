package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dto.jwt.*;
import ru.geekbrains.smartkt.exceptions.*;
import ru.geekbrains.smartkt.security.jwt.TokenUtil;
import ru.geekbrains.smartkt.services.*;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.http.*;

import lombok.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final CustomerService service;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    // создать авторизационный токен, который будет вписываться в заголовки запросов
    // при каждом обращении к сервису
    public ResponseEntity<?> createAuthToken(@RequestBody Request r) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            ERR_WRONG_AUTH), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = service.loadUserByUsername(r.getUsername());
        return ResponseEntity.ok(new Response(tokenUtil.generateToken(userDetails)));
    }
}