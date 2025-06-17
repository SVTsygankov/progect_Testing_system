package com.svtsygankov.project_servlet_java_rush.service;

public interface LoginAttemptService {
    boolean isBlocked(String login);
    void recordFailedAttempt(String login);
    void recordSuccessfulAttempt(String login);
}
