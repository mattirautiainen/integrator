package chat;

import javax.websocket.Session;

public class User {
	
	private String name;
	private int userId;
	private Session session;
	
	public User(String name, int userId, Session session){
		this.name = name;
		this.userId = userId;
		this.session = session;
	}
	
	public String getName() {
		return name;
	}
	
	public Session getSession() {
		return session;
	}
	
	public int getId() {
		return userId;
	}
	
}
