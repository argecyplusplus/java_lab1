package ru.chernyukai.projects.lab1jokebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeData;
import ru.chernyukai.projects.lab1jokebot.service.JokeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;

    //GET all
    @GetMapping
    ResponseEntity<List<Joke>> getJokes() {
        return ResponseEntity.ok(jokeService.getAllJokes());
    }

    //GET /id
    @GetMapping("/{id}")
    ResponseEntity<Joke> getJoke(@PathVariable("id") Long id){
        return jokeService.getJokeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //POST new
    @PostMapping
    ResponseEntity<Joke> addJoke(@RequestBody JokeData jokeData) {
        Joke joke = new Joke(jokeData.getText());
        return ResponseEntity.ok(jokeService.addJoke(joke));
    }

    //PUT id
    @PutMapping("/{id}")
    ResponseEntity<Joke> editJoke(@PathVariable("id") Long id, @RequestBody JokeData jokeData){
        Optional<Joke> jokeOptional = jokeService.getJokeById(id);
        if (jokeOptional.isPresent()){
            Joke joke = jokeOptional.get();
            return ResponseEntity.ok(jokeService.editJoke(joke, jokeData.getText()));
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

    //DELETE id
    @DeleteMapping("/{id}")
    ResponseEntity<Joke> deleteJoke(@PathVariable("id") Long id){
        Optional<Joke> jokeOptional = jokeService.getJokeById(id);
        if (jokeOptional.isPresent()){
            jokeService.deleteJokeById(id);
            return ResponseEntity.ok().build();
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

}
