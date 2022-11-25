package ru.geekbrains.smartkt.exceptions;

import java.util.*;

public class ValidationException extends RuntimeException {
    private final List<String> errorMessages;

    public List<String> getErrorMessages() { return errorMessages; }

    public ValidationException(List<String> messages) {
        //super(messages.stream().collect(Collectors.joining(", ")));
        super(String.join(", ", messages));
        errorMessages = messages;
    }
}