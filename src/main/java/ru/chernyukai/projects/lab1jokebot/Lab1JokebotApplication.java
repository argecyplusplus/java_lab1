package ru.chernyukai.projects.lab1jokebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Lab1JokebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab1JokebotApplication.class, args);
	}

}
