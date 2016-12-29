package integrator;

public class Room {
    
    private Double temperature;
    private Double temperature_asetus;
    private Double cooling_power;
    
    public boolean setValue(String type, Double value) {
        switch(type) {
            case "TEMP": temperature = value; return true;
            case "TEMP_ASETUS": temperature_asetus = value; return true;
            case "COOLING_POWER": cooling_power = value; return true;
            default: return false;
        }
    }
    
    public Double getValue(String type) {
        switch(type) {
            case "TEMP": return temperature;
            case "TEMP_ASETUS": return temperature_asetus;
            case "COOLING_POWER": return cooling_power;
            default: return null;
        }       
    }
}
