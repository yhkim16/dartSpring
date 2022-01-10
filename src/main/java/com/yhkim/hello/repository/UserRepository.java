package com.yhkim.hello.repository;

import com.yhkim.hello.dto.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUserName(String user_name);
    void deleteByUserName(String user_name);
}
