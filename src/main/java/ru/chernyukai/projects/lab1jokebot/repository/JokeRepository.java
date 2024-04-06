package ru.chernyukai.projects.lab1jokebot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.chernyukai.projects.lab1jokebot.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeRepository extends JpaRepository<Joke, Long>, PagingAndSortingRepository<Joke, Long> {
    //getting all jokes
    List<Joke> getJokesBy(PageRequest pageRequest);

    //getting jokes by id
    Optional<Joke> getJokeById(Long id);

    //get random joke
    @Query(value="select * from jokes order by random() limit 1", nativeQuery = true)
    Joke getRandomJoke();

}
