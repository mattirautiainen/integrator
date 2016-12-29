package integrator;

/*
 * Rajapinta halytyksien vastaanottamiseen ja lahettamiseen
 */
public interface AlarmControl {
    void addAlarmListener(AlarmListener listener);
    void sendAlarm(String shortDescription, String longDescription, String source);
}
