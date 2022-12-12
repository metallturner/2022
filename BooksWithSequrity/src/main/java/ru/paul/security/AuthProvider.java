package ru.paul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.paul.service.serviceImpl.PersonDetailServiceImpl;

import java.util.Collections;

/**
 * класс отвечает за сравнение логина и пароля. Сравнивает пароль и логин полученные от юзера
 * с паролем и логином из БД
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    private final PersonDetailServiceImpl personDetailService;

    @Autowired
    public AuthProvider(PersonDetailServiceImpl personDetailService) {
        this.personDetailService = personDetailService;
    }


    /**
     * Принимает Credentials(логин и пароль), возвращает Principal (объект с данными о пользователе - объект с PersonDetails)
     * Помимо пароля и деталей, возвращает права пользователя(пустая коллекция - все пользователи имеют
     * одинаковые права)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        UserDetails personDetails = personDetailService.loadUserByUsername(username); // получаем логин
        String password = authentication.getCredentials().toString();  // получаем пароль

        if(!personDetails.getPassword().equals(password)){
            throw new BadCredentialsException("Неверный пароль");
        }

        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
