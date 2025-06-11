package com.svtsygankov.project_servlet_java_rush.service;

import com.svtsygankov.project_servlet_java_rush.dao.UserDao;
import com.svtsygankov.project_servlet_java_rush.entity.Role;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(String email, String password, Role role) {
        User user = User.builder()
                .id(userDao.getNextId())
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        userDao.save(user);
    }
}
