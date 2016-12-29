package integrator;

import java.util.ArrayList;
import java.util.List;

public class Integrator implements ReadingSender, AlarmControl {
    
    private Room S1 = new Room();
    private Room S2 = new Room();
    
    private Log log = new Log();
    
    private List<AlarmListener> alarmListeners = new ArrayList<>();
    
    private static final Integrator instance = new Integrator();
    
    private Integrator() {}
    
    public static ReadingSender getReadingSender() {
        return instance;
    }
    
    public static AlarmControl getAlarmControl() {
        return instance;
    }
    
    @Override
	public Double getReading(String source, String type) {
		switch (source) {
		case "S1":
			return S1.getValue(type);
		case "S2":
			return S2.getValue(type);
		default:
			return null;
		}
	}

    @Override
    public boolean sendReading(String source, String type, Double reading) {
       switch(source) {
		case "S1":
			S1.setValue(type, reading);
			return true;
		case "S2":
			S2.setValue(type, reading);
			return true;
		default:
			return false;
       }
    }
    
    @Override
    public void sendAlarm(String shortDescription, String longDescription, String source) {
        Alarm alarm = new Alarm(shortDescription, longDescription, source);
        log.writeToFile(alarm);
        for(AlarmListener listener : alarmListeners) {
            listener.notifyListener(alarm);
        }
    }
    
    @Override
    public void addAlarmListener(AlarmListener listener){
        alarmListeners.add(listener);
    }
    
}