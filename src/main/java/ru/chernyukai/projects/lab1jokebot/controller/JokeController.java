package ru.chernyukai.projects.lab1jokebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.JokeData;
import ru.chernyukai.projects.lab1jokebot.service.JokeCallService;
import ru.chernyukai.projects.lab1jokebot.service.JokeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;
    private final JokeCallService jokeCallService;

    //GET all
    @GetMapping
    ResponseEntity<List<Joke>> getJokes(@RequestParam("page") int page) {
        return ResponseEntity.ok(jokeService.getAllJokes(page));
    }

    //GET /id
    @GetMapping("/{id}")
    ResponseEntity<Joke> getJoke(@PathVariable("id") Long id, @RequestParam("userId") Long userId){

        Optional<Joke> jokeOptional = jokeService.getJokeById(id);
        if (jokeOptional.isPresent()){
            Joke joke = jokeOptional.get();
            jokeCallService.addJokeCall(joke, userId);
            return ResponseEntity.ok(joke);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    //GET random joke
    @GetMapping("/random")
    ResponseEntity<Joke> getJoke(@RequestParam("userId") Long userId){
        Joke joke = jokeService.getRandomJoke();
        jokeCallService.addJokeCall(joke, userId);
        return ResponseEntity.ok(joke);
    }


    //GET top5
    @GetMapping("/top5")
    ResponseEntity<List<Joke>> getTop5Jokes(){
        return ResponseEntity.ok(jokeService.getTop5Jokes());
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
