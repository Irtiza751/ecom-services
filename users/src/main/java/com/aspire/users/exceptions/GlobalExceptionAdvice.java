package com.aspire.users.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Hashtable;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @NullMarked
    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = new Hashtable<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            Object errorValue = error.getDefaultMessage() != null ? error.getDefaultMessage() : "Not valid";
            errors.put(error.getField(), errorValue);
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @NullMarked
    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Map<String, Object>> handleDuplicateException(DuplicateKeyException exception) {
        Map<String, Object> errors = new Hashtable<>();
        errors.put("message", exception.getMessage());
        errors.put("status", HttpStatus.CONFLICT.value());
        errors.put("success", false);
        return ResponseEntity.badRequest().body(errors);
    }

    @NullMarked
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Map<String, Object>> handleNotFoundException(EntityNotFoundException exception) {
        Map<String, Object> errors = new Hashtable<>();
        errors.put("message", exception.getMessage());
        errors.put("status", HttpStatus.NOT_FOUND.value());
        errors.put("success", false);
        return ResponseEntity.badRequest().body(errors);
    }
}
