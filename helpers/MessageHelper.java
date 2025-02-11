package helpers;


import Driver.Main;
import server.ClientConnection;
import services.PromptDbService;
import services.TestDbService;
import services.UserDbService;
import java.util.ArrayList;

public class MessageHelper {
    public static String getResponse(String message, ClientConnection clientConnection) throws Exception {
        String operation = message.substring(0,3);
        if(operation.equals("000")) {
            String publicKeyString = message.substring(4);
            clientConnection.clientPublicKey = CryptoHelper.getPublicKeyFromMessage(publicKeyString);
            return "000";
        }
        String decryptedMessage = "";
        String values = "";
        Object[] updateValues = null;
        int userId = clientConnection.getClientId();
        if(message.length() > 3)  {
            values = message.substring(4);
        }
        switch(operation) {
            case "100":
                decryptedMessage = CryptoHelper.decryptMessage(values, Main.privateKey);
                Object[] credentials = StringHelper.deserialize(decryptedMessage);
                String response = UserDbService.getUser(credentials, clientConnection);
                return "100 " + CryptoHelper.encryptMessage(response, clientConnection.clientPublicKey);
            case "101":
                return "101 " + PromptDbService.getAllPrompts();
            case "102":
                Object[] attributes = StringHelper.deserialize(values);
                int userid = 0;
                int prompt = 0;
                if(attributes[0] instanceof String && attributes[1] instanceof String) {
                    userid = Integer.parseInt((String)attributes[0]);
                    prompt = Integer.parseInt((String)attributes[1]);
                }
                else return "102 -108";
                return "102 " + TestDbService.getAllTests(userid, prompt);
            case "103":
                int promptId = Integer.parseInt(values);
                return "103 " + TestDbService.getLeaderboard(promptId);
            case "200":
                return "200 " + handleInsert(values, clientConnection);
            case "300":
                 updateValues = StringHelper.deserialize(values);
                return "300 " + handleUpdate(updateValues);
            case "400":
                updateValues = StringHelper.deserialize(values);
                int value = Integer.parseInt((String)updateValues[0]);
                return "400 " + PromptDbService.delete(value, userId);
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

    private static String handleInsert(String message, ClientConnection clientConnection) throws Exception {
        Object[] values = StringHelper.deserialize(message);
        String table = (String)values[0];

        switch(table) {
            case "user":
                String userPass = CryptoHelper.decryptMessage((String)values[1], Main.privateKey);
                String response = UserDbService.insert(StringHelper.deserialize(userPass));
                return "user " + response;
            case "prompt":
                return "prompt " + PromptDbService.insert(values);
            case "test":
                return "test " + TestDbService.insert(values);
        }
        return "-1";
    }


    private static void handeDel() {

    }

    public static String createMessage(ArrayList<String> rows) {
        return StringHelper.serialize(rows,'_');
    }

}
