package ru.chernyukai.projects.lab1jokebot.service;

import org.springframework.data.domain.Page;
import ru.chernyukai.projects.lab1jokebot.model.User;

import java.util.Optional;

public interface UserService {
    void registration(String username, String password);

    Page<User> getAllUsers (int page);


    Optional<User> getUserById (Long id);

    Optional<User> editUserAuthority(Long id, boolean isModerator);


}
