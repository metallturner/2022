package ru.paul.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.paul.models.Book;
import ru.paul.service.serviceImpl.BookServiceImpl;
import ru.paul.utils.BookErrorResponse;
import ru.paul.utils.BookNotFoundEx;
import ru.paul.utils.BooksNotCreatedEx;
import ru.paul.utils.BooksNotFoundEx;

import javax.validation.Valid;
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
    public List <Book> getAllBooks() {
        return bookServiceImpl.findAll();
    }


    @GetMapping({"{id}"})
    public Book getBookById(@PathVariable("id") int bookId) {
        return bookServiceImpl.findById(bookId);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createBook(@RequestBody @Valid Book book, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List <FieldError> errors = br.getFieldErrors();
            for (FieldError e : errors) {
                sb.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new BooksNotCreatedEx(sb.toString());
        }

        bookServiceImpl.save(book);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateBook(@RequestBody @Valid Book book, BindingResult br) {
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List <FieldError> errors = br.getFieldErrors();
            for (FieldError e : errors) {
                sb.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new BooksNotCreatedEx(sb.toString());
        }

        bookServiceImpl.update(book);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int bookId) {
        bookServiceImpl.delete(bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Методы ниже ловят исключения и вовращают JSON(BookErrorResponse) с описанием проблемы
     * и статусом. Исключения выбрасываются из контроллера(либо прямо из него (createBook), либо пробрасываются в него из
     * BookService).
     */

    @ExceptionHandler  //для поиска и удаления по id
    private ResponseEntity<BookErrorResponse> handleEx(BookNotFoundEx b){
        BookErrorResponse bookErrorResponse = new BookErrorResponse("Такой книги нет");
        return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler   // если бд пустая
    private ResponseEntity<BookErrorResponse> handleEx(BooksNotFoundEx b){
        BookErrorResponse bookErrorResponse = new BookErrorResponse("Библиотека пустая");
        return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler  // если ввели невалидные данные при добавлении или изменении
    private ResponseEntity<BookErrorResponse> handleEx(BooksNotCreatedEx b){
        BookErrorResponse bookErrorResponse = new BookErrorResponse(b.getMessage());
        return new ResponseEntity<>(bookErrorResponse, HttpStatus.BAD_REQUEST);
    }


}
