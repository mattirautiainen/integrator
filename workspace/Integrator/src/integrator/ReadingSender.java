package integrator;

/*
 * Rajapinta mittaustietojen lahettamiseen ja hakemiseen
 */
public interface ReadingSender {
	public Double getReading(String source, String type);
    public boolean sendReading(String source, String type, Double reading);
}
