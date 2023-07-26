package com.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.Quote;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteAndReadHelper {
    public static File quotesFile = new File("quotes.json");

    public static List<Quote> readQuotes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(quotesFile.exists() && quotesFile.length() > 1) {
                int idSeed = Quote.getIdSeed();
                List<Quote> quotes = objectMapper.readValue(quotesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Quote.class));
                Quote.setIdSeed(idSeed);
                return quotes;
            }
        } catch(IOException e) {
        e.printStackTrace();
        }
        return new ArrayList<Quote>();
    }

    public static void setQuoteIdSeed() {
        List<Quote> quotes = readQuotes();
        if(!quotes.isEmpty()) {
            int idSeed = quotes.get(quotes.size() - 1).getId() + 1;
            Quote.setIdSeed(idSeed);
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
}
