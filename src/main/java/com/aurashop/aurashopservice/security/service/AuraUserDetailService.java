package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.entity.User;
import com.aurashop.aurashopservice.security.dto.AuraUserDetail;
import com.aurashop.aurashopservice.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuraUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public AuraUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> usersByPhoneNumber = userRepository.getUserByPhoneNumber(username);

        return usersByPhoneNumber.isEmpty() ? null : new AuraUserDetail(usersByPhoneNumber.getFirst());
    }
}
