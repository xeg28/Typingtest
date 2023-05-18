package application;

import java.io.Serializable;

public class Quote implements Serializable
{
    private static final long serialVersionUID = 1L;
    String quote;
    String quoteTitle;
    String highestUser;
    double highestTest;
    
    public Quote() {
    }
    
    public Quote(String quote) {
        this.quote = quote;
    }
    
    public void setRecordHolder(String user) {
    	this.highestUser = user;
    }
    
    
    @Override
    public String toString() {
        return this.quote;
    }
}