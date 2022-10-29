package application;

import java.io.Serializable;

public class Quote implements Serializable
{
    private static final long serialVersionUID = 1L;
    String quote;
    String quoteTitle;
    
    public Quote() {
    }
    
    public Quote(final String quote) {
        this.quote = quote;
    }
    
    public void setQuote(final String quote) {
        this.quote = quote;
    }
    
    @Override
    public String toString() {
        return this.quote;
    }
}