package ru.geekbrains.smartkt.exceptions;

public class LoggableException extends RuntimeException {
    private final String logMessage;
    public LoggableException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    public String getLogMessage() { return logMessage; }
}