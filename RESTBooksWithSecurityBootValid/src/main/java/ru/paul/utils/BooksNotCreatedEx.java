package ru.paul.utils;

public class BooksNotCreatedEx extends RuntimeException{
    public BooksNotCreatedEx(String message) {
        super(message);
    }
}
