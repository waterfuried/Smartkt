package ru.geekbrains.smartkt.exceptions;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppError {
    private int statusCode;
    private String message;
}