package ru.geekbrains.smartkt.exceptions;

import java.util.*;

import lombok.*;

@NoArgsConstructor
@Data
public class DataValidationError {
    private List<String> errorMessages;

    public DataValidationError(List<String> messages) { errorMessages = messages; }
}