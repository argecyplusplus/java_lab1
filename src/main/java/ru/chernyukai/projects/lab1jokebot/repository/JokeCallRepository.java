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

    //SELECT joke_id, count (*) from calls group by joke_id order by count limit 5
    /*@Query("select joke_id FROM calls GROUP BY joke_id ORDER BY COUNT(*) DESC limit 5")
    List<Long> getTop5JokesIds();*/
}