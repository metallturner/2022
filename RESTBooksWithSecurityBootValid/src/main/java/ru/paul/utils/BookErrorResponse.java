package ru.paul.utils;

public class BookErrorResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookErrorResponse(String message) {
        this.message = message;
    }
}
