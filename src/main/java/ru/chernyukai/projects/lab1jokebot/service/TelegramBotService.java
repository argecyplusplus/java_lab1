package ru.chernyukai.projects.lab1jokebot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.repository.JokeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

public class TelegramBotService {
    private final TelegramBot telegramBot;
    private final JokeRepository jokeRepository;

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeRepository jokeRepository) {
        this.telegramBot = telegramBot;
        this.jokeRepository = jokeRepository;
        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::buttonClickReact);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private void buttonClickReact(Update update) {
        if (update.message() == null) return;
        try {
            Random random = new Random();
            List<Joke> jokes = jokeRepository.getJokesBy();
            //Commands
            String answerText;
            if (update.message().text().startsWith("/start")) {
                answerText = "Вас приветствует бот анекдотов!\nЧтобы случайный получить анкедот, введите /randomjoke\nЧтобы получить номера акектодов, введите /alljokes\nЧтобы получить анекдот по номеру, введите /joke НОМЕР";
            } else if (update.message().text().startsWith("/help")) {
                answerText = "Доступные команды:\n\nЧтобы случайный получить анкедот, введите /randomjoke\nЧтобы получить номера акектодов, введите /alljokes\nЧтобы получить анекдот по номеру, введите /joke НОМЕР";
            } else if (update.message().text().startsWith("/randomjoke")) {
                int randomIndex = random.nextInt(jokes.size());
                Joke joke = jokes.get(randomIndex);
                answerText = "Анекдот #" + joke.getId() + "\n" + joke.getText();
            } else if (update.message().text().startsWith("/alljokes")) {
                answerText = "Все номера анекдотов:\n";
                for (Joke j : jokes) {
                    answerText += "#" + j.getId() + " ";
                }
            } else if (update.message().text().startsWith("/joke")) {
                try {
                    Long index = Long.parseLong(update.message().text().substring(6));
                    Optional<Joke> jokeOptional = jokeRepository.getJokeById(index);
                    if (jokeOptional.isPresent()) {
                        answerText = "Анекдот #" + index + "\n" + jokeOptional.get().getText();
                    } else {
                        answerText = "Нет анекдота с таким номером";
                    }
                } catch (Exception e) {
                    answerText = "Ошибка ввода команды";
                }
            } else {
                answerText = "Нет такой команды!\nЧтобы случайный получить анкедот, введите /randomjoke\nЧтобы получить номера акектодов, введите /alljokes\nЧтобы получить анекдот по номеру, введите /joke НОМЕР";
            }
            SendMessage request = new SendMessage(update.message().chat().id(), answerText)
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(update.message().messageId());
            this.telegramBot.execute(request);
        }
        catch (Exception e){
            return;
        }
    }

}
