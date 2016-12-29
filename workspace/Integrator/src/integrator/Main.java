package integrator;

public class Main {
	
    
    public static void main(String args[]) {
        
        
    	ReadingSender integraattori = Integrator.getReadingSender();
        integraattori.sendReading("S1", "TEMP", 5.0);
        System.out.println(integraattori.getReading("S1","TEMP"));
        integraattori.sendReading("S2", "TEMP", 4.0);
        System.out.println(integraattori.getReading("S1","TEMP"));
        System.out.println(integraattori.getReading("S2","TEMP"));
        
        AlarmListener listener = new AlarmListener() {

    		@Override
    		public void notifyListener(Alarm alarm) {
    			System.out.println(alarm);
    		}
        	
        };
        
        AlarmControl alarmControl = Integrator.getAlarmControl();
        alarmControl.addAlarmListener(listener);
        alarmControl.sendAlarm("TULIPALO", "", "S1");
        alarmControl.sendAlarm("HALYTYS", "", "S2");
        
    }
}