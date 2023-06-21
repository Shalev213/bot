package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class InfoBot extends TelegramLongPollingBot {
    private int callsCounter=0;

    @Override
    public String getBotToken() {
        return "6275640731:AAFi_nPFeyMEu5HSXDSzqWG7p1ZJSfzswDY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        User fullName = update.getMessage().getFrom();
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage= new SendMessage();
        sendMessage.setChatId(chatId);
        String send = "Hey " + fullName.getFirstName() + ", welcome to my chat!!";
        if (message.equals("/start")){
            callsCounter++;
            System.out.println(callsCounter);
        }

        sendMessage.setText(send);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        System.out.println("First name: " + fullName.getFirstName() + " ,last name: " + fullName.getLastName() + ". The message is: " + message);

    }

    @Override
    public String getBotUsername() {
        return "info236Bot";
    }
}
