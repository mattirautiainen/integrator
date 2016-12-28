package chat;

public class Message {
	
	private String text;
	private int timestamp;
	
	public Message(String text, int timestamp) {
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
}
