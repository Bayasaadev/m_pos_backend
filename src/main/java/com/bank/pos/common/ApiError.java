package com.bank.pos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @JsonProperty("message")
    private String message;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("details")
    private List<FieldError> details;

    public ApiError() {
    }

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(String message, String responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public ApiError(String message, List<FieldError> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<FieldError> getDetails() {
        return details;
    }

    public void setDetails(List<FieldError> details) {
        this.details = details;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {
        @JsonProperty("field")
        private String field;

        @JsonProperty("error")
        private String error;

        public FieldError() {
        }

        public FieldError(String field, String error) {
            this.field = field;
            this.error = error;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}


