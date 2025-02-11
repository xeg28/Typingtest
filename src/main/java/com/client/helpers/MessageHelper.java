package com.client.helpers;

import java.util.regex.Pattern;

public class MessageHelper {

//    public static String serialize(String message) {
//        return message.replaceAll("\\\\", "\\\\\\\\");
//    }

    public static String serialize(Object... args) {
        StringBuilder sb = new StringBuilder();

        for (Object arg : args) {
            if (sb.length() > 0) {
                sb.append("|"); // Add a delimiter between values
            }
            if (arg instanceof String) {
                // Escape delimiter and backslash in strings
                sb.append(((String) arg).replace("\\", "\\\\")
                        .replace("|", "\\|")
                        .replace("~","\\~")
                        .replace("\n", "~"));
            } else {
                // Append non-string values directly
                sb.append(arg.toString());
            }
        }
        return sb.toString();
    }

    public static String[] deserialize(String serialized, char delimiter) {
        String[] parts = serialized.split("(?<!\\\\)"+ Pattern.quote(""+delimiter));
        String[] result = new String[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = parts[i].replace("\\"+delimiter,""+delimiter).replace("\\\\", "\\");
        }
        return result;
    }

    public static Object[] deserialize(String serialized) {
        String[] parts = serialized.split("(?<!\\\\)\\|"); // Split on unescaped '|'
        Object[] result = new Object[parts.length];

        for (int i = 0; i < parts.length; i++) {
            // Unescape strings (restore escaped backslashes and delimiters)
            result[i] = parts[i].replace("\\|", "|")
                    .replace("\\\\", "\\")
                    .replaceAll("(?<!\\\\)~", "\n")
                    .replaceAll("\\\\~", "~");
        }
        return result;
    }

    public static boolean isErrorCode(String response) {
        return Pattern.matches("^-\\d+$", response);
    }
}
