package ru.geekbrains.smartkt.prefs;

import org.springframework.http.*;

public class ReqResponse<T> extends ResponseEntity<T> {
    ReqResponse(T t) { super(t, HttpStatus.OK); }
    ReqResponse(T t, HttpStatus status) { super(t, status); }
}