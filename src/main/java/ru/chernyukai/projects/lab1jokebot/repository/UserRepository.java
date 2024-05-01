package ru.chernyukai.projects.lab1jokebot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chernyukai.projects.lab1jokebot.model.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> getUsersBy();

    Optional<User> getUserById(Long id);


}