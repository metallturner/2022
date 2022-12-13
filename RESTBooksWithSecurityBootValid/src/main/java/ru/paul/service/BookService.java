package ru.paul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paul.models.Book;
import ru.paul.repositories.BookRepository;
import ru.paul.service.serviceImpl.BookServiceImpl;
import ru.paul.utils.BookNotFoundEx;
import ru.paul.utils.BooksNotFoundEx;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService implements BookServiceImpl {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        if(bookRepository.findAll().isEmpty()){
            throw new BooksNotFoundEx();
        }
        return bookRepository.findAll();
    }

    public Book findById(int id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundEx();
        }
        return book.get();
    }

    /**
     * манипуляции с новой книгой и сетами для того, чтобы могли передавать книгу с любым Id
     * и чтобы он перед добавлением затирался, а БД сама добавит нужный Id
     */

    @Transactional
    public void save(Book book){
        Book saveBook = new Book();
        saveBook.setAuthor(book.getAuthor());
        saveBook.setLocation(book.getLocation());
        saveBook.setName(book.getName());
        bookRepository.save(saveBook);
    }

    @Transactional
    public void update(Book book){
      Book bookUpgrade = bookRepository.findById(book.getId()).get();
        bookUpgrade.setName(book.getName());
        bookUpgrade.setLocation(book.getLocation());
        bookUpgrade.setAuthor(book.getAuthor());
    }

    @Transactional
    public void delete(int id){

        if(bookRepository.findById(id).isEmpty()){
            throw new BookNotFoundEx();
        }
        bookRepository.deleteById(id);
    }


}
