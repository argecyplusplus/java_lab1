package ru.chernyukai.projects.lab1jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeCall;
import ru.chernyukai.projects.lab1jokebot.repository.JokeCallRepository;
import ru.chernyukai.projects.lab1jokebot.repository.JokeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService{
    private final JokeRepository jokeRepository;
    private final JokeCallRepository jokeCallRepository;

    @Override
    public Joke addJoke(Joke joke) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = currentDate.format(formatter);
        joke.setCreateDate(date);
        joke.setUpdateDate(date);
        return jokeRepository.save(joke);
    }

    @Override
    public Joke editJoke(Joke joke, String newText) {

        joke.setText(newText);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = currentDate.format(formatter);
        joke.setUpdateDate(date);

        return jokeRepository.save(joke);
    }

    @Override
    public void deleteJokeById(Long id) {
        jokeRepository.deleteById(id);
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.getJokesBy();
    }

    @Override
    public Joke getRandomJoke(){
        return jokeRepository.getRandomJoke();
    }


    @Override
    public List<Joke> getTop5Jokes(){

        List<Joke> jokeCalls = null;
        /*jokeCalls= jokeCallRepository.getTop5JokesIds();

        List<Joke> top5Jokes = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            System.out.println(jokeCalls.get(i));

            Long jokeId = Arrays.stream(jokeCalls.get(i)).toList().get(0);

            Joke joke = jokeRepository.getJokeById(jokeId).get();
            top5Jokes.add(joke);
        }
        */
        return jokeCalls;


    }

    @Override
    public Optional<Joke> getJokeById(Long id) {
        return jokeRepository.findById(id);
    }
}
