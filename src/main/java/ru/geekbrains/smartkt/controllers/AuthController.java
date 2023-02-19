package ru.geekbrains.smartkt.controllers;

import ru.geekbrains.smartkt.dto.jwt.*;
import ru.geekbrains.smartkt.exceptions.*;
import ru.geekbrains.smartkt.security.jwt.TokenUtil;
import ru.geekbrains.smartkt.services.*;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
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
        } catch (AuthenticationException ex) {
            HttpStatus status;
            String message;
            if (ex instanceof BadCredentialsException) {
                status = HttpStatus.UNAUTHORIZED;
                message = ERR_WRONG_AUTH;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = ex.getMessage();
            }
            return new ResponseEntity<>(new AppError(status.value(), message), status);
        }
        UserDetails userDetails = service.loadUserByUsername(r.getUsername());
        return ResponseEntity.ok(new Response(tokenUtil.generateToken(userDetails)));
    }
}