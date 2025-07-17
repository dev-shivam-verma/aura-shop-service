package com.aurashop.aurashopservice.security.repository;

import com.aurashop.aurashopservice.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getUserByPhoneNumber(String phoneNumber);

    List<User> findByPhoneNumber(String phoneNumber);
}