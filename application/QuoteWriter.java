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
    private ArrayList<Quote> quotes;
    private String dir;
    private File newFile;
    
    QuoteWriter() {
        this.quotes = new ArrayList<Quote>();
        this.dir = System.getProperty("user.dir");
        this.newFile = new File(String.valueOf(this.dir) + "\\Quotes.txt");
    }
    
    public void writeQuote(String newQuote) {
        try {
            Quote quote = new Quote(newQuote);
            this.quotes.add(quote);
            FileOutputStream file = new FileOutputStream(this.newFile);
            ObjectOutputStream oStream = new ObjectOutputStream(file);
            oStream.writeObject(this.quotes);
            file.close();
            oStream.close();
            this.readQuote();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeQuote() {
        try {
            FileOutputStream file = new FileOutputStream(this.newFile);
            ObjectOutputStream oStream = new ObjectOutputStream(file);
            oStream.writeObject(this.quotes);
            file.close();
            oStream.close();
            this.readQuote();
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
    
    public void readQuote() throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.newFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.quotes = (ArrayList<Quote>)objectInputStream.readObject();
        }
        catch (FileNotFoundException e2) {
            this.newFile.createNewFile();
            this.writeQuote("When he utilizes combined energy, his fighting men become as it were like unto rolling logs or stones. For it is the nature of a log or stone to remain motionless on level ground, and to move when on a slope; if four-cornered, to come to a standstill, but if round-shaped, to go rolling down.");
            this.writeQuote("But I try not to think with my gut. If I'm serious about understanding the world, thinking with anything besides my brain, as tempting as that might be, is likely to get me into trouble.");
            this.writeQuote("I don't want to live someone else's idea of how to live. Don't ask me to do that. I don't want to find out one day that I'm at the end of someone else's life.");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Quote> getQuotes() {
        return this.quotes;
    }
}