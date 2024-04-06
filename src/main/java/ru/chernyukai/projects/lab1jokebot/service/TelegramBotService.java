package ru.chernyukai.projects.lab1jokebot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.chernyukai.projects.lab1jokebot.model.Joke;
import ru.chernyukai.projects.lab1jokebot.repository.JokeRepository;
import ru.chernyukai.projects.lab1jokebot.service.JokeCallService;
import ru.chernyukai.projects.lab1jokebot.service.JokeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

public class TelegramBotService {
    private final TelegramBot telegramBot;

    private final JokeService jokeService;
    private final JokeCallService jokeCallService;

    public TelegramBotService(@Autowired TelegramBot telegramBot, JokeRepository jokeRepository, JokeService jokeService, JokeCallService jokeCallService) {
        this.telegramBot = telegramBot;
        this.jokeService = jokeService;
        this.jokeCallService = jokeCallService;
        this.telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::buttonClickReact);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, Throwable::printStackTrace);
    }

    private void buttonClickReact(Update update) {

        if (update.message() == null) return;
        try {
            String msgText = update.message().text();
            Long userId = update.message().from().id();
            String answerText;

            if (msgText.startsWith("/start")) {
                answerText = "Вас приветствует бот анекдотов!\nЧтобы случайный получить анкедот, введите /randomjoke\nЧтобы получить номера акектодов, введите /alljokes НОМЕР_СТРАНИЦЫ\nЧтобы получить анекдот по номеру, введите /joke НОМЕР";
            } else if (msgText.startsWith("/help")) {
                answerText = "Доступные команды:\n\nЧтобы случайный получить анкедот, введите /randomjoke\nЧтобы получить номера акектодов, введите /alljokes НОМЕР_СТРАНИЦЫ\nЧтобы получить анекдот по номеру, введите /joke НОМЕР";
            } else if (msgText.startsWith("/randomjoke")) {
                //вывести рандомную шутку
                Joke joke = jokeService.getRandomJoke();
                jokeCallService.addJokeCall(joke, userId);
                answerText =joke.getId() + ") " + joke.getText();
            } else if (msgText.startsWith("/alljokes")) {
                //вывести все номера шуток (нужный лист)
                try{
                    int page = Integer.parseInt(msgText.substring(10));

                    List<Joke> allJokes = jokeService.getAllJokes(page);
                    List<Long> indexes = new ArrayList<>();
                    for (Joke joke : allJokes){
                        indexes.add(joke.getId());
                    }
                    if (!indexes.isEmpty()){
                        answerText = "Список шуток:\n";
                        for (Long id : indexes){
                            String spoiler = jokeService.getJokeById(id).get().getText().substring(0, 15);
                            answerText += id + ") " + spoiler +"...\n";
                        }
                    }
                    else {
                        answerText = "Данный лист пуст!";
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    answerText ="Ошибка ввода страницы";
                }
            } else if (msgText.startsWith("/joke")) {
                //вывести шутку по номеру
                try{
                    Long id = Long.parseLong(msgText.substring(6));

                    Optional<Joke> jokeOptional = jokeService.getJokeById(id);

                    if(jokeOptional.isPresent()){
                        Joke joke = jokeOptional.get();
                        jokeCallService.addJokeCall(joke, userId);
                        answerText = joke.getId() + ") " + joke.getText();
                    }
                    else{
                        answerText = "Шутка не найдена!";
                    }
                }
                catch (Exception e){
                    answerText = "Ошибка ввода";

                }
            } else {
                answerText = "Ошибка ввода";
            }
            SendMessage request = new SendMessage(update.message().chat().id(), answerText)
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(update.message().messageId());
            this.telegramBot.execute(request);
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
    }

}
