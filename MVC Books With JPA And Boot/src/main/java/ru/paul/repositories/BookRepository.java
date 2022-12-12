package ru.paul.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paul.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
