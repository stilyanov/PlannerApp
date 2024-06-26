package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.User;
import com.plannerapp.model.entity.dto.UserLoginDTO;
import com.plannerapp.model.entity.dto.UserRegisterDTO;
import com.plannerapp.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDTO registerDTO) {
        Optional<User> existingUser = this.userRepository.findByUsernameOrEmail(registerDTO.getUsername(), registerDTO.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> optionalUser = this.userRepository.findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            return false;
        }

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword());

        if (!matches) {
            return false;
        }

        this.userSession.login(optionalUser.get().getId(), loginDTO.getUsername());

        return true;
    }

    public void logout() {
        this.userSession.logout();
    }
}
