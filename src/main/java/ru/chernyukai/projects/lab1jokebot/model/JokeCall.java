package ru.chernyukai.projects.lab1jokebot.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter //Генерируем геттеры
@Setter //Генерируем сеттеры
@ToString
@Entity(name = "calls")
@Table(name = "calls")
public class JokeCall {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "call_seq")
    @SequenceGenerator(name = "call_seq", sequenceName = "call_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @JoinColumn (name="joke_id")
    @ManyToOne
    @NotNull
    private Joke joke;

    @Column (name="call_time")
    private Time callTime;

    @Column (name="user_id")
    private Long userId;


}
