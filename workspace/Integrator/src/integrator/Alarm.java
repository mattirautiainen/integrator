package integrator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm {
    private final String longDescription;
    private final String shortDescription;
    private final String source;
    private final Date date;
    
    public Alarm(String shortDescription, String longDescription, String source) {
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
        this.source = source;
        date = new Date();       
    }
    
    public String getLongDescription() {
        return longDescription;
    }
    
    public String getShortDescription() {
        return shortDescription;
    }
    
    public String getSource() {
        return source;
    }
    
    public Date getDate(){
        return date;
    }
    
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date) + " " +shortDescription;
    }
}

