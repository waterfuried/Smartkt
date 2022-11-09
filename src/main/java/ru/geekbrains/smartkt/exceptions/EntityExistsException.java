package ru.geekbrains.smartkt.exceptions;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String message) { super(message); }
}