package ru.paul.service.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;

public interface PersonDetailServiceImpl {
    public UserDetails loadUserByUsername(String username);
}
