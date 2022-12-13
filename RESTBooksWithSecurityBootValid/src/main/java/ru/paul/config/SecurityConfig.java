package ru.paul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.paul.service.PersonDetailService;
import ru.paul.service.serviceImpl.PersonDetailServiceImpl;


/**
 * главный класс для сесурити. Настраивается аутентификация и авторизация
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private final PersonDetailService personDetailService;

   @Autowired
    public SecurityConfig(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //настраивает аутентификацию
        auth.userDetailsService(personDetailService);
    }

    /**
     *отключаем csrf чтобы работали все методы в контроллере без присвоения ролей
     * Но, если мы просто отключим защиту, то сможем входить в приложение без аутентификации
     * так как переопределив метод ниже, мы отказались от базовой страницы аутентификации.
     * Для того чтобы сохранить базовую страницу authorizeRequests().anyRequest().authenticated().and().httpBasic();
     */

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();


}

    @Bean
    PasswordEncoder getPasswordEncoder(){
       return NoOpPasswordEncoder.getInstance();
    }

}
