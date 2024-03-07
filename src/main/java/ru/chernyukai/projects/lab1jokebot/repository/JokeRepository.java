package ru.chernyukai.projects.lab1jokebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernyukai.projects.lab1jokebot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeRepository extends JpaRepository<Joke, Long> {
    //getting all jokes
    List<Joke> getJokesBy();

    //getting jokes by id
    Optional<Joke> getJokeById(Long id);
}
