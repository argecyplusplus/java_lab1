package ru.chernyukai.projects.lab1jokebot.service;


import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeCall;

public interface JokeCallService {
    JokeCall  addJokeCall (Joke joke, Long userId);
}
