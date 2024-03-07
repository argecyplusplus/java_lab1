package ru.chernyukai.projects.lab1jokebot.model;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter //Генерируем геттеры
@Setter //Генерируем сеттеры
@ToString
public final class JokeData {
    private String text;
}
