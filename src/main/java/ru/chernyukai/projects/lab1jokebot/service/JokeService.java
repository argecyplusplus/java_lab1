package ru.chernyukai.projects.lab1jokebot.service;

import ru.chernyukai.projects.lab1jokebot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeService {

    Joke addJoke(Joke joke);

    Joke editJokeById(Long id, String new_text);

    void deleteJokeById(Long id);

    List<Joke> getAllJokes();

    Optional<Joke> getJokeById(Long id);
}
