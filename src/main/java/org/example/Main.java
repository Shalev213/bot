package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {

        InfoBot infoBot = new InfoBot();
        StartWindow startWindow = new StartWindow(infoBot);
        startWindow.showWindow();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new InfoBot());

        } catch (TelegramApiException e) {
//            throw new RuntimeException();
            System.out.println("Error" + e);
        }
    }
}