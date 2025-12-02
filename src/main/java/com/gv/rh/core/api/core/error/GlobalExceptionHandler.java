package com.gv.rh.core.api.core.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String message,
            String path,
            List<ErrorResponse.FieldErrorInfo> fieldErrors
    ) {
        return new ErrorResponse(
                LocalDateTime.now(),
                path,
                message,
                status.name(),
                status.value(),
                fieldErrors
        );
    }

    // 1) Errores de validación en @RequestBody (DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldErrorInfo> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ErrorResponse.FieldErrorInfo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        ErrorResponse body = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Errores de validación",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 2) Errores de validación en parámetros (@RequestParam, @PathVariable, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldErrorInfo> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(cv -> new ErrorResponse.FieldErrorInfo(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()
                ))
                .toList();

        ErrorResponse body = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Errores de validación",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 3) NotFound genérico para cualquier cosa
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse body = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 4) Genérico (último recurso)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse body = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno. Contacta al administrador.",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
