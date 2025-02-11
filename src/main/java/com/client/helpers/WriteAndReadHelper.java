package com.client.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.client.models.Quote;
import com.client.models.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteAndReadHelper {
    public static File quotesFile = new File("quotes.json");
    public static File usersFile = new File("users.json");

    public static List<Quote> readQuotes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(quotesFile.exists() && quotesFile.length() > 1) {
                int idSeed = Quote.getIdSeed();
                List<Quote> quotes = objectMapper.readValue(quotesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Quote.class));
                //List<Quote> quotes = objectMapper.readValue(quotesFile, new TypeReference<List<Quote>>() {});
                Quote.setIdSeed(idSeed);
                return quotes;
            }
        } catch(IOException e) {
        e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<User> readUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(usersFile.exists() && usersFile.length() > 1) {
                int idSeed = User.getIdSeed();
                List<User> users = objectMapper.readValue(usersFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                User.setIdSeed(idSeed);
                return users;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    public static void setQuoteIdSeed() {
        List<Quote> quotes = readQuotes();
        if(!quotes.isEmpty()) {
            int idSeed = quotes.get(quotes.size() - 1).getId() + 1;
            Quote.setIdSeed(idSeed);
        }
    }

    public static void setUserIdSeed() {
        List<User> users = readUsers();
        if(!users.isEmpty()) {
            int idSeed = users.get(users.size() - 1).getId() + 1;
            User.setIdSeed(idSeed);
        }
    }

    public static void writeQuote(Quote quote)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Quote> quotes = readQuotes();
            quotes.add(quote);
            objectMapper.writeValue(quotesFile, quotes);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUser(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<User> users = readUsers();
            users.add(user);
            objectMapper.writeValue(usersFile, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateUsers(List<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.writeValue(usersFile, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuotes(List<Quote> quotes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(quotesFile, quotes);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteQuote(int index) {
        List<Quote> quotes = QuoteHelper.getQuotes();
        quotes.remove(index);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(quotesFile, quotes);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
