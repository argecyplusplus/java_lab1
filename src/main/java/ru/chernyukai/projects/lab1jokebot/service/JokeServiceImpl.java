package ru.chernyukai.projects.lab1jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.repository.JokeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService{
    private final JokeRepository jokeRepository;


    @Override
    public Joke addJoke(Joke joke) {
        return jokeRepository.save(joke);
    }

    @Override
    public Joke editJokeById(Long id, String new_text) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        if (jokeOptional.isPresent()){
            Joke original_joke = jokeOptional.get();
            original_joke.setText(new_text);
            return jokeRepository.save(original_joke);
        }
        else{
            return null;
        }
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
    public Optional<Joke> getJokeById(Long id) {
        return jokeRepository.findById(id);
    }
}
