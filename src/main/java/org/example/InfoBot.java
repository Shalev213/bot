package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class InfoBot extends TelegramLongPollingBot {
    private int callsCounter = 0;
    private int uniqueUserCounter = 0;
    private User fullName;
    private String message;
    private Queue<String> messages;
    private Map<Long, Integer> userMessageCounts;
    private Map<Long,Integer> uniqueUserMap;
    private Integer numOfMessage;
    private int maxMessages = 0;
    private JokesModel jokesModel;
    private CatFactsModel catFactsModel;
    private NumFactsModel numFactsModel;
    private QuotesModel quotesModel;
    private CountriesModel countriesModel;
    private String activeUserName ="No one";
    private Long chatId;
    private  String countryName ="ISR";
    private Map<Long,Integer> levelsMap;
    private int countJoke;
    private int countCountry;
    private int countQuote;
    private int countCatFact;
    private int countNumFact;
    private Map<Integer, Integer> countApiMap;
    private String popularActivity = "Nothing ";
    private List<String> namesApi = List.of("Joke", "Quote", "Number fact", "Cat fact", "Country");
    private final static List<String> selectedCheckBoxesToString = new ArrayList<>();

    public InfoBot() {
        this.countApiMap = new HashMap<>();
        this.uniqueUserMap = new HashMap<>();
        this.userMessageCounts = new HashMap<>();
 new Thread(()-> {
     try {
         HttpResponse<String> countries = Unirest.get("https://restcountries.com/v2/alpha/" + countryName).asString();
         ObjectMapper objectMapper1 = new ObjectMapper();
         countriesModel =objectMapper1.readValue(countries.getBody(), CountriesModel.class);
         // System.out.println(countriesModel.getName() + ": capital- " + countriesModel.getCapital() + ", population- " +countriesModel.getPopulation());

         HttpResponse<String> quotes = Unirest.get("https://api.quotable.io/random").asString();
         ObjectMapper objectMapper2 = new ObjectMapper();
         quotesModel  =objectMapper2.readValue(quotes.getBody(), QuotesModel.class);
         //System.out.println(quotesModel.getAuthor() + ": " + quotesModel.getContent());

         HttpResponse<String> numFacts = Unirest.get("http://numbersapi.com/random?json").asString();
         ObjectMapper objectMapper3 = new ObjectMapper();
         numFactsModel =objectMapper3.readValue(numFacts.getBody(), NumFactsModel.class);
//        System.out.println(numFactsModel.getText());

         HttpResponse<String> catFacts = Unirest.get("https://catfact.ninja/fact").asString();
         ObjectMapper objectMapper4 = new ObjectMapper();
         catFactsModel =objectMapper4.readValue(catFacts.getBody(), CatFactsModel.class);
//              System.out.println(catFactsModel.getFact());

         HttpResponse<String> jokes = Unirest.get("https://official-joke-api.appspot.com/random_joke").asString();
         ObjectMapper objectMapper5 = new ObjectMapper();
         jokesModel = objectMapper5.readValue(jokes.getBody(), JokesModel.class);
//        System.out.println(jokesModel.getSetup() + " " + jokesModel.getPunchline());

     }catch (Exception e){
         e.printStackTrace();
     }
 }).start();

    }

    public static List<String> getSelectedCheckBoxesToString(){
        return selectedCheckBoxesToString;
    }

    @Override
    public String getBotToken() {
        return "6275640731:AAFi_nPFeyMEu5HSXDSzqWG7p1ZJSfzswDY";
    }
    @Override
    public void onUpdateReceived(Update update) {
        message = update.getMessage().getText();
        fullName = update.getMessage().getFrom();
        chatId = update.getMessage().getChatId();
        Integer date = update.getMessage().getDate();
        SendMessage sendMessage= new SendMessage();
        sendMessage.setChatId(chatId);
        numOfMessage = this.uniqueUserMap.get(chatId);
        String history = "Name: " + fullName + ", message: " + message + ", date: " + date;
        String firstSend = "Hey " + fullName.getFirstName() + ", welcome to my chat!! " + "\n" + "Please choose one option to send: " + "\n '" + selectedCheckBoxesToString.get(0) + "', '" + selectedCheckBoxesToString.get(1) + "' or '" + selectedCheckBoxesToString.get(2) + "'." ;
        if (numOfMessage == null){
            this.uniqueUserCounter++;
            this.uniqueUserMap.put(chatId, 1);
        } else if (numOfMessage >= 1) {

            this.uniqueUserMap.put(chatId, numOfMessage+1);
        }
        if (message.equals("/start")){
            callsCounter++;
            System.out.println(callsCounter);
            sendMessage.setText(firstSend);

        }

        for (int i = 0; i < selectedCheckBoxesToString.size() ; i++) {

            if (selectedCheckBoxesToString.get(i).equals("Joke") && message.equals("Joke")) {

                this.countJoke++;
                countApiMap.put(0,countJoke);
                sendMessage.setText(jokesModel.getSetup() + " " + jokesModel.getPunchline());

            }else if (selectedCheckBoxesToString.get(i).equals("Quote") && message.equals("Quote")) {
                this.countQuote++;
                countApiMap.put(1,countQuote);
                sendMessage.setText(quotesModel.getAuthor() + ": " + quotesModel.getContent());
//
            }else if (selectedCheckBoxesToString.get(i).equals("Number fact") && message.equals("Number fact")) {
                this.countNumFact++;
                countApiMap.put(2,countNumFact);
                sendMessage.setText(numFactsModel.getText());

            }
            else if (selectedCheckBoxesToString.get(i).equals("Cat fact") && message.equals("Cat fact")) {
                this.countCatFact++;
                countApiMap.put(3,countCatFact);
                sendMessage.setText(catFactsModel.getFact());

            }
            else if (selectedCheckBoxesToString.get(i).equals("Country") && message.equals("Country")) {
                this.countCountry++;
                countApiMap.put(4,countCountry);
                sendMessage.setText("Please send a alpha code of a country (For example: ISR for Israel.)");

//                sendMessage.setText(quotesModel.getAuthor() + ": " + quotesModel.getContent());
            }
        }
        send(sendMessage);
        System.out.println("Name: " + fullName.getFirstName() + " " + fullName.getLastName() + ", message: " + message + ", date: " + date.toString());
//        Systema.out.println("First name: " + fullName.getFirstName() + " ,last name: " + fullName.getLastName() + ". The message is: " + message);

        if (userMessageCounts.containsKey(chatId)) {
            int count = userMessageCounts.get(chatId);
            userMessageCounts.put(chatId, count + 1);
        } else {
            userMessageCounts.put(chatId, 1);
        }
    }

    private void send (SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    public String getActiveUserName() {
        if (userMessageCounts.get(chatId) != null && userMessageCounts.get(chatId) > maxMessages) {
            maxMessages = userMessageCounts.get(chatId);
            activeUserName = fullName.getFirstName();
        }
        return activeUserName;
    }
    public int getMessagesByUser(Long chatId) {
        return userMessageCounts.getOrDefault(chatId, 0);
    }
    public synchronized String getPopularActivity(){
        int max = 0;
        for (int i = 0; i < namesApi.size() ; i++) {
            if (countApiMap.get(i) != null && countApiMap.get(i) > max){
                max = countApiMap.get(i);
                popularActivity = namesApi.get(i);
            }
        }
        return popularActivity;
    }
    public  Queue<String> lastTenMessages(){

        return null;

    }

    public CountriesModel getCountriesModel() {
        return countriesModel;
    }

    public String getActiveUser() {
        return activeUserName;
    }

    public User getFullName() {
        return fullName;
    }

    public String getMessage() {
        return message;
    }

    public int getCallsCounter() {
        return callsCounter;
    }

    public int getUniqueUserCounter() {
        return uniqueUserCounter;
    }

    @Override
    public String getBotUsername() {
        return "info236Bot";
    }
//    public void displayMessagesQueue() {
//        System.out.println("Messages from " + fullName + ":");
//        if (!messages.isEmpty()) {
//            for (String message : messages) {
//                System.out.println("- " + message);
//            }
//        }
//    }


    public Queue<String> getMessagesQueue() {
        Queue<String> messagesQueue = new LinkedList<>(messages);

        while (messagesQueue.size() > 10) {
            messagesQueue.poll();
        }
        return messagesQueue;
    }
}
