package com.client.controllers;

import com.client.helpers.*;
import com.client.models.Quote;
import com.client.models.Test;
import com.client.models.User;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Queue;

public class RequestController extends Thread {
    private static Queue<String> responses = new LinkedList<>();

    public void run() {
        try {
            while(true) {
                Thread.sleep(100);
                if(responses.isEmpty()) continue;
                handleResponse();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public static String getResponse() {
//        String temp = response;
//        response = null;
//        return temp;
//    }

    private static void handleResponse() {
        String code_response = responses.remove();
        String code = code_response.substring(0, 3);
        String response = code_response.substring(4);
        System.out.println(code_response);
        switch(code) {
            case "000":
                handlePublicKey(response);
                break;
            case "100":
                handleUserRequest(response);
                break;
            case "101":
                handlePromptRequest(response);
                break;
            case "102":
                handleUserTest(response);
                break;
            case "103":
                handleLeaderBoardRequest(response);
                break;
            case "200":
                String[] table_code = response.split(" ");
                if(MessageHelper.isErrorCode(table_code[1])) handleInsertError(table_code[0], table_code[1]);
                break;
            case "400":
                handleQuoteDelete(response);
                break;
        }
    }

    public static void handlePublicKey(String stringPublicKey) {
        ServerController.publicKey = CryptoHelper.getPublicKeyFromMessage(stringPublicKey);
        KeyPair keypair = CryptoHelper.generateKeyPair();
        ServerController.privateKey = keypair.getPrivate();
        PublicKey publicKey = keypair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        ServerController.sendMessage("000 " + publicKeyString);
    }

    public static void addResponse(String message) {
        responses.add(message);
    }

    public static void sendQuoteInsert(Quote quote) {
        String title = quote.getTitle();
        String text = quote.getQuote();
        String username = quote.getUsername();
        String request = "200 " + MessageHelper.serialize("prompt", title, text, username);
        ServerController.sendMessage(request);
    }

    public static void sendUserRequest(String username, String password) {
        String credentials = MessageHelper.serialize(username, password);
        credentials =  CryptoHelper.encryptMessage(credentials, ServerController.publicKey);
        ServerController.sendMessage("100 " + credentials);
    }

    public static void sendQuotesRequest() {
        ServerController.sendMessage("101");
    }
    public static void sendTestInsert(int userId, int quoteId, double wpm) {
        String request = "200 " + MessageHelper.serialize("test", userId, quoteId, wpm);
        ServerController.sendMessage(request);
    }

    public static void sendUserInsert(String username, String password) {
        String credentials = MessageHelper.serialize(username, password);
        String encrypted = CryptoHelper.encryptMessage(credentials, ServerController.publicKey);
        String request = MessageHelper.serialize("200 user", encrypted);
        ServerController.sendMessage(request);
    }

    public static void sendUserTestRequest(int userId, int quoteId) {
        String request ="102 "+MessageHelper.serialize(userId, quoteId);
        ServerController.sendMessage(request);
    }

    public static void sendLeaderboardRequest(int quoteId) {
        String request = "103 "+quoteId;
        ServerController.sendMessage(request);
    }

    public static void sendUserUpdate(User currentUser) {
        int userId = currentUser.getId();
        int totalTests = currentUser.getTests();
        double averageWPM = currentUser.getAverageWPM();
        double bestWPM = currentUser.getBestWPM();
        String request = "300 "+MessageHelper.serialize("user", userId, totalTests, averageWPM, bestWPM);
        ServerController.sendMessage(request);
    }


    public static void sendQuoteDelete(int quoteId) {
        ServerController.sendMessage("400 " + MessageHelper.serialize(quoteId));
    }
    public static void handleLeaderBoardRequest(String response) {
        if(response.equals("")) {
            QuoteHelper.leaderBoard = new ArrayList<>();
            return;
        }
        ArrayList<Test> leaderboard = new ArrayList<>();
        String[] table = MessageHelper.deserialize(response, '_');
        for(String rowString : table) {
            Object[] row = MessageHelper.deserialize(rowString);
            Test test = new Test();
            test.setId(Integer.parseInt((String)row[0]));
            test.setUsername((String)row[1]);
            test.setQuoteId(Integer.parseInt((String)row[2]));
            test.setWpm(Double.parseDouble((String)row[3]));
            test.setTestTime((String)row[4]);
            leaderboard.add(test);
        }
        QuoteHelper.leaderBoard = leaderboard;
    }

    private static void handleUserTest(String response) {
        if(response.equals("NO RESULT")) {
            // show alert message
            return;
        }
        String[] rows = MessageHelper.deserialize(response, '_');
        ArrayList<Test> tests = new ArrayList<>();
        for(String rowString : rows) {
            Test test = new Test();
            Object[] row = MessageHelper.deserialize(rowString);
            test.setId(Integer.parseInt((String)row[0]));
            test.setUsername((String)row[1]);
            test.setQuoteId(Integer.parseInt((String)row[2]));
            test.setWpm(Double.parseDouble((String)row[3]));
            test.setTestTime((String)row[4]);
            tests.add(test);
        }
        UserHelper.userTestsForCurrentQuote = tests;
    }

    private static void handleUserRequest(String response) {
        response = CryptoHelper.decryptMessage(response, ServerController.privateKey);
        if(MessageHelper.isErrorCode(response)) {
            if(response.equals("-100")) {
                AlertHelper.errors.add("Login ERROR|Username was not found");
            } else if(response.equals("-101")) {
                AlertHelper.errors.add("Login ERROR|Password is incorrect.");
            }
            return;
        }
        Object[] row = MessageHelper.deserialize(response);
        User user = new User();
        user.setId((Integer.parseInt((String)row[0])));
        user.setName((String)row[1]);
        user.setTests(Integer.parseInt((String)row[2]));
        user.setAverageWPM(Double.parseDouble((String)row[3]));
        user.setBestWPM(Double.parseDouble((String)row[4]));
        user.setDateCreated((String)row[5]);

        UserHelper.currentUser = user;
    }

    private static void handlePromptRequest(String response) {
        if(response.equals("")) {
            QuoteHelper.setQuotes(new ArrayList<>());
            return;
        }

        ArrayList<Quote> quotes = new ArrayList<>();
        String[] rows = MessageHelper.deserialize(response, '_');
        for(String rowString : rows) {
            Quote quote = new Quote();
            Object[] row = MessageHelper.deserialize(rowString);
            quote.setId(Integer.parseInt((String)row[0]));
            quote.setTitle((String)row[1]);
            quote.setQuote((String)row[2]);
            quote.setUsername((String)row[3]);
            quotes.add(quote);
        }

        QuoteHelper.setQuotes(quotes);
    }

    private static void handleInsertError(String table, String code) {
        switch(table) {
            case "user":
                if(code.equals("-200"))
                    AlertHelper.errors.add("Create User ERROR|That username is not available");
                break;
        }
    }

    private static void handleQuoteDelete(String response) {
        if(MessageHelper.isErrorCode(response)) {
            if(response.equals("-401")) {
                AlertHelper.errors.add("Delete ERROR|You can't delete that quote because it you are not the owner");
            }
            else if(response.equals("-402")) {
                AlertHelper.errors.add("Delete ERROR|Login to delete a quote");
            }
            return;
        }
        int quoteId = Integer.parseInt(response);
        QuoteHelper.deleteQuote(quoteId);

    }
}
