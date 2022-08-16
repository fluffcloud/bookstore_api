package com.example.bookstore_final_app.error_handling;

public class APIException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public APIException(String message){
        super(message);
    }
}
