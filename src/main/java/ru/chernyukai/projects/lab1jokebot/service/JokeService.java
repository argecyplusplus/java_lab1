package ru.chernyukai.projects.lab1jokebot.service;

import org.springframework.data.domain.Page;
import ru.chernyukai.projects.lab1jokebot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {

    Joke addJoke(Joke joke);

    Joke editJoke(Joke joke, String newText);

    List<Joke> getTop5Jokes();

    void deleteJokeById(Long id);

    List<Joke> getAllJokes(int page);

    Optional<Joke> getJokeById(Long id);

    Joke getRandomJoke ();
}
