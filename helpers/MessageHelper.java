package helpers;


import services.PromptDbService;
import services.TestDbService;
import services.UserDbService;

import java.util.ArrayList;

public class MessageHelper {
    public static String getResponse(String message) {
        String operation = message.substring(0,3);

        switch(operation) {
            case "100":
                String username = message.substring(4);
                return "100 " + UserDbService.getUser(username);
            case "101":
                return "101 " + PromptDbService.getAllPrompts();
            case "102":
                Object[] attributes = StringHelper.deserialize(message.substring(4));
                int userid = 0;
                int prompt = 0;
                if(attributes[0] instanceof String && attributes[1] instanceof String) {
                    userid = Integer.parseInt((String)attributes[0]);
                    prompt = Integer.parseInt((String)attributes[1]);
                }
                else return null;
                return "102 " + TestDbService.getAllTests(userid, prompt);
            case "103":
                int promptId = Integer.parseInt(message.substring(4));
                return "103 " + TestDbService.getLeaderboard(promptId);
            case "200":
                String values = message.substring(4);
                return "200 " + handleInsert(operation, values);
            case "300":
                Object[] updateValues = StringHelper.deserialize(message.substring(4));
                return "300 " + handleUpdate(updateValues);
        }
        return null;
    }

    private static String handleUpdate(Object[] values) {
        String table = (String)values[0];
        if(table.equals("user")) {
            return UserDbService.alterUser(values);
        }
        else if(table.equals("prompt")){
            //possible feature
        }
        return "";
    }

    private static String handleInsert(String operation, String message) {
        StringBuilder currentString = new StringBuilder();
        boolean stringFlag = false;
        boolean escapedSymbol = false;
        String table = "";
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for(char c : message.toCharArray()) {
            if(stringFlag) {
                if(!escapedSymbol && c == '\\') {
                    escapedSymbol = true;
                    continue;
                }
                if(!escapedSymbol && c == '\'') {
                    stringFlag = false;
                    continue;
                }
                if(escapedSymbol && c == '\\') {
                    currentString.append(c);
                    escapedSymbol = false;
                    continue;
                }

                escapedSymbol = false;
            }
            else if(c == '?') {
                table = currentString.toString();
                currentString = new StringBuilder();
                continue;
            }
            else if(c == ',') {
                values.add(currentString.toString());
                currentString = new StringBuilder();
                continue;
            }
            else if(c == '\'') {
                stringFlag = true;
                continue;
            }
            currentString.append(c);
        }
        values.add(currentString.toString());

        switch(table) {
            case "user":
            case "prompt":
            case "test":
                return String.valueOf(TestDbService.insert(values));
        }
        return "-1";
    }

    private static void handleGet() {

    }

    private static void handeDel() {

    }

    public static String createMessage(ArrayList<String> rows) {
        return StringHelper.serialize(rows,'_');
    }

}
