package ru.site.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.site.model.User;
import ru.site.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
