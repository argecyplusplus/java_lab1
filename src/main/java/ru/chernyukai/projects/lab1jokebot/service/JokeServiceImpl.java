package ru.chernyukai.projects.lab1jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Joke> getAllJokes(int page) {
        int size = 5;
        List<Joke> jokes =  jokeRepository.getJokesBy();

        //Вывести страницу
        int start = Math.min(page * size, jokes.size());
        int end = Math.min((page + 1) * size, jokes.size());
        List<Joke> usersOnPage = jokes.subList(start, end);
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(usersOnPage, pageable, jokes.size());
    }

    @Override
    public Joke getRandomJoke(){
        return jokeRepository.getRandomJoke();
    }


    @Override
    public List<Joke> getTop5Jokes(){

        List<Long> idsOfTop5 = jokeCallRepository.getIdsOfTop5();

        List<Joke> jokes = new ArrayList<>();
        for(Long id:idsOfTop5){
            jokes.add(jokeRepository.getJokeById(id).get());
        }

        return jokes;
    }

    @Override
    public Optional<Joke> getJokeById(Long id) {
        return jokeRepository.findById(id);
    }
}
