package ru.chernyukai.projects.lab1jokebot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.model.User;
import ru.chernyukai.projects.lab1jokebot.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<Page<User>> getAllUsers(@RequestParam("page") int page) {
        return ResponseEntity.ok(userService.getAllUsers(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable("id") Long id) {

        Optional<User> userOptional = userService.getUserById(id);

        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/{id}")
    ResponseEntity<User> editUserAuthority(@PathVariable("id") Long id, @RequestParam("moder") boolean isModerator) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(userService.editUserAuthority(id, isModerator).get());
        }
        else{
            return  ResponseEntity.notFound().build();
        }

    }


}
