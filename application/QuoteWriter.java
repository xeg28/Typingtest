package application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;

public class QuoteWriter
{
    static private ArrayList<Quote> quotes;
    static private String dir;
    
    QuoteWriter() {
        quotes = new ArrayList<Quote>();
        dir = System.getProperty("user.dir");
    }
   
    public static int findQuote(String quote) {
    	for(int i = 0; i < quotes.size(); i++) {
    		if(quote.equals(quotes.get(i).toString())) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    public static void updateAndSave(Quote quote) {
    	quotes.set(findQuote(quote.toString()), quote);
    	writeQuote();
    	
    }
    
    public void writeQuote(String newQuote) {
        try {
            Quote quote = new Quote(newQuote);
            quotes.add(quote);
            FileOutputStream file = new FileOutputStream(new File(String.valueOf(dir) + "\\Quotes.txt"));
            ObjectOutputStream oStream = new ObjectOutputStream(file);
            oStream.writeObject(this.quotes);
            file.close();
            oStream.close();
            readQuote();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeQuote() {
        try {
            FileOutputStream file = new FileOutputStream(new File(String.valueOf(dir) + "\\Quotes.txt"));
            ObjectOutputStream oStream = new ObjectOutputStream(file);
            oStream.writeObject(quotes);
            file.close();
            oStream.close();
            readQuote();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteQuote(int index) {
    	if(quotes.size() == 0) {
    		return;
    	}
        this.quotes.remove(index);
    }
    
    public static void readQuote() throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(String.valueOf(dir) + "\\Quotes.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            quotes = (ArrayList<Quote>)objectInputStream.readObject();
        }
        catch (FileNotFoundException e2) {
        	new File(String.valueOf(dir) + "\\Quotes.txt").createNewFile();
            quotes.add(new Quote("When he utilizes combined energy, his fighting men become as it were like unto rolling logs or stones. For it is the nature of a log or stone to remain motionless on level ground, and to move when on a slope; if four-cornered, to come to a standstill, but if round-shaped, to go rolling down."));
            quotes.add(new Quote("But I try not to think with my gut. If I'm serious about understanding the world, thinking with anything besides my brain, as tempting as that might be, is likely to get me into trouble."));
            quotes.add(new Quote("I don't want to live someone else's idea of how to live. Don't ask me to do that. I don't want to find out one day that I'm at the end of someone else's life."));
            writeQuote();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Quote> getQuotes() {
        return this.quotes;
    }
}