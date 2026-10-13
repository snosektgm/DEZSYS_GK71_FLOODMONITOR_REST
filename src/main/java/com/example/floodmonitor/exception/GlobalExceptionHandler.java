package com.example.floodmonitor.exception;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExceptionHandler {
@ExceptionHandler(ResponseStatusException.class)
public ResponseEntity<Map<String, Object>> handleResponseEntity(ResponseStatusException exception, WebRequest request) {
    HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
    return fehler(status, exception.getReason(), request);
}
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
    return fehler(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
}
private ResponseEntity<Map<String, Object>> fehler(HttpStatus status, String text, WebRequest request) {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String path = request.getDescription(false).replace("uri=", "");
    Map<String, Object> f =new LinkedHashMap<>();
    f.put("timestamp", timestamp);
    f.put("status", status.value());
    f.put("error", status.getReasonPhrase());
    f.put("message", text);
    f.put("path", path);

    return new ResponseEntity<>(f, status);
}
}
