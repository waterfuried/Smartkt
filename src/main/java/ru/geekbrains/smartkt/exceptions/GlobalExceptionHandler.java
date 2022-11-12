package ru.geekbrains.smartkt.exceptions;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// специализированный компонент, предназначенный для перехвата всех исключений приложения
@ControllerAdvice
public class GlobalExceptionHandler {
    // методы-обработчики исключений, в аннотации можно указывать классы исключений
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), ex.getMessage()),
                HttpStatus.CONFLICT);
    }
}