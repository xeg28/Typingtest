package com.helpers;

import com.models.User;
import com.views.Header;
import com.views.LoadUser;
import javafx.scene.control.ComboBox;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserHelper {
    public static List<User> users = WriteAndReadHelper.readUsers();

    public static User currentUser;

    public static void updateUsers() {
        users = WriteAndReadHelper.readUsers();
    }

    public static void addUsersToList() {
        ComboBox<String> userBox = LoadUser.users;
        userBox.getItems().clear();
        for(int i = 0; i < users.size(); i++) {
            userBox.getItems().add(users.get(i).getName());
        }
    }

    public static User getUser(int index) {
        return users.get(index);
    }

    public static void deleteUser(User user) {
        users.remove(user);
        if(currentUser == user) setDefaultUser();

        QuoteHelper.deleteUserInLeaderboards(user.getId());
        WriteAndReadHelper.updateUsers(users);
    }

    private static void setDefaultUser() {
        currentUser = null;
        Header.usernameLabel.setText("Default User");
        Header.highWPM.setText("Highest WPM: 0.0");
        Header.avgWPMLabel.setText("Average WPM: 0.0");
    }

    public static void deleteTopFiveForQuote(int quoteId) {
        for(User user : users) {
            user.getTopFive().remove(quoteId);
        }
        WriteAndReadHelper.updateUsers(users);
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
        currentUser.setTests(currentUser.getTests() + 1);
        currentUser.setTotalSumWPM(currentUser.getTotalSumWPM() + wpm);
        if(currentUser.getBestWPM() < wpm) {
            currentUser.setBestWPM(wpm);
        }
        DecimalFormat format1 = new DecimalFormat("0.#");
        Double avg = Double.parseDouble(format1.format(currentUser.getTotalSumWPM() / currentUser.getTests()));
        currentUser.setAverageWPM(avg);
        setTopFiveForCurrentQuote(wpm);

        WriteAndReadHelper.updateUsers(users);

        Header.highWPM.setText("Highest WPM: " + currentUser.getBestWPM());
        Header.avgWPMLabel.setText("Average WPM: " + currentUser.getAverageWPM());


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
