package com.client.helpers;

import com.client.controllers.RequestController;
import com.client.controllers.ServerController;
import com.client.models.Test;
import com.client.models.User;
import com.client.views.Header;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserHelper {
    public static List<User> users = WriteAndReadHelper.readUsers();
    public static List<Test> userTestsForCurrentQuote;

    public static User currentUser;
    public static double lastWPM;

    public static void updateUsers() {
        if(!ServerController.isConnected()) users = WriteAndReadHelper.readUsers();
    }


    public static User getUser(int index) {
        return users.get(index);
    }

    public static void deleteUser(User user) {
        users.remove(user);
        if(currentUser == user) setDefaultUser();

        WriteAndReadHelper.updateUsers(users);
    }

    private static void setDefaultUser() {
        currentUser = null;
        Header.usernameLabel.setText("Default User");
        Header.highWPM.setText("Highest WPM: 0.0");
        Header.avgWPMLabel.setText("Average WPM: 0.0");
    }

    public static void resetUser() {
        currentUser = null;
        userTestsForCurrentQuote = new ArrayList<>();
    }
    public static User getUserById(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void updateUserStats(double wpm) {
        if(currentUser.getBestWPM() < wpm) {
            currentUser.setBestWPM(wpm);
        }
        DecimalFormat format1 = new DecimalFormat("0.#");
        double currentAvg = currentUser.getAverageWPM();
        int currentTotal = currentUser.getTests();
        Double avg = Double.parseDouble(format1.format((currentAvg*currentTotal+wpm) / (currentTotal+1)));
        currentUser.setTests(currentTotal + 1);
        currentUser.setAverageWPM(avg);
        RequestController.sendUserUpdate(currentUser);
//        setTopFiveForCurrentQuote(wpm);
        RequestController.sendTestInsert(currentUser.getId(), QuoteHelper.currentQuote.getId(), wpm);

//        WriteAndReadHelper.updateUsers(users);

        Header.highWPM.setText("Highest WPM: " + currentUser.getBestWPM());
        Header.avgWPMLabel.setText("Average WPM: " + currentUser.getAverageWPM());
        Header.userTooltip.setText("Highest WPM: " + currentUser.getBestWPM() +
                "\nAverage WPM: " + currentUser.getAverageWPM());

    }


    private static void setTopFiveForCurrentQuote(double wpm) {
        int currQuoteId = QuoteHelper.currentQuote.getId();
        HashMap<Integer, List<Double>> topFive = currentUser.getTopFive();
        List<Double> topFiveTests = topFive.get(currQuoteId);
        if(topFiveTests == null) {
            topFiveTests = new ArrayList<>();
            topFiveTests.add(wpm);
            topFive.put(currQuoteId, topFiveTests);
        } else {
            if(topFiveTests.size() == 5) {
                if(wpm < topFiveTests.get(4))
                    return;
                topFiveTests.set(4, wpm);
            }
            else {
                topFiveTests.add(wpm);
            }

            int j = topFiveTests.size() - 1;
            while(j > 0 && wpm > topFiveTests.get(j-1)) {
                topFiveTests.set(j , topFiveTests.get(j-1));
                j--;
            }
            topFiveTests.set(j, wpm);
        }
    }

}
