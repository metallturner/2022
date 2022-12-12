package ru.paul.service.serviceImpl;

import ru.paul.models.Book;

import java.util.List;

public interface BookServiceImpl {
    public List<Book> findAll();
    public void save(Book book);
    public void update(Book book);
    public void delete(int id);
    public Book findById(int id);

}
