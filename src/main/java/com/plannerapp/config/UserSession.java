package com.plannerapp.config;

import com.plannerapp.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private long userId;

    private String username;

    public void login(long id, String username) {
        this.userId = id;
        this.username = username;
    }

    public boolean isLoggedIn() {
        return userId > 0;
    }

    public void logout() {
        userId = 0;
        username = null;
    }

    public Long id() {
        return userId;
    }

    public String username() {
        return username;
    }
}
