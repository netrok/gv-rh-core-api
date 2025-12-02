package com.gv.rh.core.api.core.error;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String path;
    private String message;
    private String error;          // tipo corto: BAD_REQUEST, NOT_FOUND, etc.
    private int status;            // 400, 404, 500...
    private List<FieldErrorInfo> fieldErrors; // para validaciones por campo

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, String path, String message,
                         String error, int status, List<FieldErrorInfo> fieldErrors) {
        this.timestamp = timestamp;
        this.path = path;
        this.message = message;
        this.error = error;
        this.status = status;
        this.fieldErrors = fieldErrors;
    }

    // getters/setters...

    public static class FieldErrorInfo {
        private String field;
        private String message;

        public FieldErrorInfo() {
        }

        public FieldErrorInfo(String field, String message) {
            this.field = field;
            this.message = message;
        }

        // getters/setters...
    }
}
