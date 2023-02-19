package ru.geekbrains.smartkt.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

// специализированный компонент, предназначенный для перехвата всех исключений приложения
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // методы-обработчики исключений, в аннотации можно указывать классы исключений
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException ex) {
        String logMessage = ex.getLogMessage();
        log.error(ex.getMessage() + (logMessage == null || logMessage.isBlank() ? "" : ": "+logMessage), ex);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DataValidationError> catchValidationException(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new DataValidationError(ex.getErrorMessages()), HttpStatus.BAD_REQUEST);
    }

    // после изменения метода проверок это исключение никогда не будет сгенерировано
    // обработка оставлена как часть истории
    @ExceptionHandler
    public ResponseEntity<AppError> catchEntityExistsException(EntityExistsException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), ex.getMessage()),
                HttpStatus.CONFLICT);
    }
}