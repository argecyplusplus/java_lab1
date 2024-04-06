package ru.chernyukai.projects.lab1jokebot.service;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeCall;
import ru.chernyukai.projects.lab1jokebot.repository.JokeCallRepository;

import java.sql.Time;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JokeCallServiceImpl implements JokeCallService{
    private final JokeCallRepository jokeCallRepository;
    @Override
    public JokeCall addJokeCall (Joke joke, Long userId){
        Date currentTime = new Date();
        JokeCall jokeCall = new JokeCall(
                null,
                joke,
                new Time(currentTime.getTime()),
                userId
        );

        return jokeCallRepository.save(jokeCall);
    }


}
