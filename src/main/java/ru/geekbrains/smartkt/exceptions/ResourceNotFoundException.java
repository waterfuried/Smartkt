package ru.geekbrains.smartkt.exceptions;

public class ResourceNotFoundException extends LoggableException {
    public ResourceNotFoundException(String message, String logMessage) { super(message, logMessage); }
}