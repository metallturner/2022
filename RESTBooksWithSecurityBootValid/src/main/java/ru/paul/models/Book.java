package ru.paul.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Ошибка добавления или редактирования. Не указан автор")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Ошибка добавления или редактирования. Не указана локация")
    @Column(name = "location")
    private String location;

    @NotEmpty(message = "Ошибка добавления или редактирования. Не указано название")
    @Column(name = "name")
    private String name;

    public Book(String author, String location, String name) {
        this.author = author;
        this.location = location;
        this.name = name;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
