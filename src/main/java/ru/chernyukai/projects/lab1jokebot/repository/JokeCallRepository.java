package ru.chernyukai.projects.lab1jokebot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeCall;

import java.util.List;
import java.util.Optional;

public interface JokeCallRepository extends JpaRepository<JokeCall, Long> {
    //get all calls
    List<JokeCall> getJokeCallsBy();

    //get top 5 joke_ids by popularity
    @Query(value="SELECT joke_id from calls group by joke_id order by count (*) desc limit 5", nativeQuery = true)
    List<Long> getIdsOfTop5();
}