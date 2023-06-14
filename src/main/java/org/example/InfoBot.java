package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class InfoBot extends TelegramLongPollingBot {
    @Override
    public String getBotToken() {
        return "6275640731:AAFi_nPFeyMEu5HSXDSzqWG7p1ZJSfzswDY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        User fullName = update.getMessage().getFrom();
        System.out.println("First name: " + fullName.getFirstName() + " ,last name: " + fullName.getLastName() + ". The message is: " + message);
    }

    @Override
    public String getBotUsername() {
        return "info236Bot";
    }
}
