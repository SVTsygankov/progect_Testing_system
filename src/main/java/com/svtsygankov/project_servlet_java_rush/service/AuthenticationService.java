package com.svtsygankov.project_servlet_java_rush.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.util.CredentialsExtractor.extract;

@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginAttemptService loginAttemptService;
    private final UserService userService;

    public boolean register(HttpServletRequest req) {
        var credentials = extract(req);
        if(userService.isExist(credentials.login())){
            return false;
        }
        userService.createUser(credentials.login(), credentials.password());
        return true;
    }
    public boolean authenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var credentials = extract(req);

        if (loginAttemptService.isBlocked(credentials.login())) {
//            resp.sendError(429); // 429
            req.setAttribute("authMessageError", "User is blocked (2 min)");
            return false;
        }

        var optionalUser = userService.findUserByCredentials(credentials.login(), credentials.password());

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            loginAttemptService.recordSuccessfulAttempt(credentials.login());
            req.getSession().setAttribute("user", user);
            return true;

        } else {
            loginAttemptService.recordFailedAttempt(credentials.login());
            return false;
        }
    }
}
