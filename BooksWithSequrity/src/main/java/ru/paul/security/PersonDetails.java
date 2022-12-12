package ru.paul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.paul.models.Person;

import java.util.Collection;

/**
 * класс является некой оберткой над юзерами. Принято для получения информации о юзере использовать
 * отдельный класс.
 * в getAuthorities добавляем роли юзера(что разрешено, что запрещено делать)
 */

public class PersonDetails implements UserDetails {

    private Person user;

    @Autowired
    public PersonDetails(Person user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson(){
        return this.user;
    }
}
