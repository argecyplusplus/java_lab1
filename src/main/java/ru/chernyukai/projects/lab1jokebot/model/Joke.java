package ru.chernyukai.projects.lab1jokebot.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter //Генерируем геттеры
@Setter //Генерируем сеттеры
@ToString
@Entity (name = "jokes")
@Table (name = "jokes")
public final class Joke {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "text")
    private String text;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "update_date")
    private String updateDate;

}

