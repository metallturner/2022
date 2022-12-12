package ru.paul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paul.models.Person;
import ru.paul.repositories.PersonRepository;
import ru.paul.security.PersonDetails;
import ru.paul.service.serviceImpl.PersonDetailServiceImpl;

import java.util.Optional;

/**
 * сервис над оберткой над юзерами, имплементируем UserDetailsService для того
 * чтобы сесурити знал, что данный сервис сделан для того чтобы загружать инфу о юзере.
 * Благодаря тому что в PersonDetails имплементим интерфейс UserDetails,
 * метод loadByUserName может возвращать PersonDetails
 */
@Service
@Transactional(readOnly = true)
public class PersonDetailService implements UserDetailsService, PersonDetailServiceImpl {

    private final PersonRepository userRepository;

    @Autowired
    public PersonDetailService(PersonRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = userRepository.findByLogin(username);

        if(person.isEmpty()){
            throw new UsernameNotFoundException("Такого пользователя нет");
        }
        return new PersonDetails(person.get());

    }
}
