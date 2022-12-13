package ru.paul.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "password")
    String password;

    @Column(name = "login")
    String login;

    public Person() {
    }

    public Person(String password, String login) {
        this.password = password;
        this.login = login;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
