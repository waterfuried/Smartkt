package ru.geekbrains.smartkt.dto.jwt;

import lombok.*;

@Data
public class Request {
    private String username;
    private String password;
}