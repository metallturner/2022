package ru.paul.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paul.models.Book;
import ru.paul.service.serviceImpl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookServiceImpl bookServiceImpl;   //внедрение зависимостей, зависимость от абстрации BookServiceImpl и тд пм.

    @Autowired
    public BookController(@Qualifier("bookService") BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        if (bookServiceImpl.findAll() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookServiceImpl.findAll(), HttpStatus.OK);
    }

    @GetMapping({"{id}"})
    public ResponseEntity<Book> getBookById(@PathVariable("id") int bookId) {
        if (bookServiceImpl.findById(bookId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookServiceImpl.findById(bookId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Book>> createBook(@RequestBody Book book) {
        bookServiceImpl.save(book);
        return new ResponseEntity<>(bookServiceImpl.findAll(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        bookServiceImpl.update(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<List<Book>> deleteBook(@PathVariable("id") int bookId) {
        bookServiceImpl.delete(bookId);
        return new ResponseEntity<>(bookServiceImpl.findAll(), HttpStatus.OK);
    }

}
