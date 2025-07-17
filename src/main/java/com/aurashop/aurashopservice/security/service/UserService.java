package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.entity.Authority;
import com.aurashop.aurashopservice.security.entity.User;
import com.aurashop.aurashopservice.security.repository.AuthorityRepository;
import com.aurashop.aurashopservice.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        List<User> users = userRepository.findByPhoneNumber(phoneNumber);

        return users.isEmpty() ? null : users.getFirst();
    }

    public Authority findAuthorityByName(String authorityName) {
        List<Authority> authorities = authorityRepository.findByName(authorityName);

        return authorities.isEmpty() ? null : authorities.getFirst();
    }
}
