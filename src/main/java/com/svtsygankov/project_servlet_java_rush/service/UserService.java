package com.svtsygankov.project_servlet_java_rush.service;

import com.svtsygankov.project_servlet_java_rush.dao.UserDao;
import com.svtsygankov.project_servlet_java_rush.entity.Role;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(String login, String password) {
        User user = User.builder()
                .id(userDao.getNextId())
                .login(login)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        userDao.save(user);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public Optional<User> findUserByCredentials(String login, String password) {
        List<User> users = userDao.findAll();

        return users.stream().
                filter(user -> user.getLogin().equals(login)).
                filter((user ->passwordEncoder.matches(password, user.getPassword()))).
                findFirst();
    }

    public boolean isExist(String login) {
        var count = userDao.findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .count();
        return count != 0;
    }
}
