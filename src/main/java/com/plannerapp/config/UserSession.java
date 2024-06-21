package com.plannerapp.config;

import com.plannerapp.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private long userId;

    private String username;

    public void login(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
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
