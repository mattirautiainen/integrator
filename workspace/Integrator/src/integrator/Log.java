package integrator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Log {
    
     public void writeToFile(Alarm alarm) {
    
        Writer output = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            output = new BufferedWriter(new FileWriter("log.txt",true));
            output.append(dateFormat.format(alarm.getDate()) + " " + alarm.getShortDescription() 
                    + ": " + alarm.getSource());
            output.append("\r\n");
            output.append(alarm.getLongDescription() );
            output.append("\r\n");
        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
        	try {
        		if(output != null)
        			output.close();
        	}  catch (IOException ex) {
				ex.printStackTrace();
			} 
        }
    }
}
