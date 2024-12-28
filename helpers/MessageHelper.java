package helpers;


import services.PromptDbService;
import services.TestDbService;
import services.UserDbService;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MessageHelper {
    public static String getResponse(String message) {
        String operation = message.substring(0,3);

        switch(operation) {
            case "100":
                String username = message.substring(4);
                return UserDbService.getUser(username);
            case "101":
                return PromptDbService.getAllPrompts();
            case "102":
                Object[] attributes = StringHelper.deserialize(message.substring(4));
                int userid = 0;
                int testid = 0;
                if(attributes[0] instanceof String && attributes[1] instanceof String) {
                    userid = Integer.parseInt((String)attributes[0]);
                    testid = Integer.parseInt((String)attributes[1]);
                }
                else return null;
                return TestDbService.getAllTests(userid, testid);
            case "200":
                String values = message.substring(4);
                handleInsert(operation, values);
                break;
        }
        return null;
    }

    private static void handleInsert(String operation, String message) {
        StringBuilder currentString = new StringBuilder();
        boolean stringFlag = false;
        boolean parsingAttribute = false;
        String table = "";
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for(char c : message.toCharArray()) {
            if(stringFlag) {
                if(c == '\'') stringFlag = false;
            }
            else if(c == '?') {
                table = currentString.toString();
                currentString = new StringBuilder();
                continue;
            }
            else if(c == '=') {
                columns.add(currentString.toString());
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
            }
            currentString.append(c);
        }
        values.add(currentString.toString());

        System.out.println("Table: "+table);
        System.out.println("Operation: INSERT");
        for(int i = 0; i < columns.size(); i++) {
            System.out.println(columns.get(i) + ": " + values.get(i));
        }
    }

    private static void handleGet() {

    }

    private static void handeDel() {

    }

    public static String createMessage(ArrayList<String> rows) {
        return StringHelper.serialize(rows,'\n');
    }

}
