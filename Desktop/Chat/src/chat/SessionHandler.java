package chat;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

@ApplicationScoped
public class SessionHandler {
	
    private final Map<Session,Message> incompleteMessages = new HashMap<>();
    private final Map<Session,User> users = new HashMap<>();
    
    public User getUser(Session session) {
    	return users.get(session);
    }
    
    /** @param type incMessage || message || enter*/
    public JsonObject createMessage(String username, 
    		Message message, String type) {
    	
    	SimpleDateFormat sdf = 
    			new SimpleDateFormat("HH:mm:ss.SSS");
    	
    	JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
        		.add("action", type)
        		.add("timestamp", sdf.format(new Date()))
        		.add("username", username)
                .add("message", message.getText())
                .build();
        
        return addMessage;
    }
    
    public void refreshIncompleteMessages() {
    	JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    	for(Map.Entry<Session, Message> entry
    			: incompleteMessages.entrySet()) {
    		jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("username",getUser(entry.getKey()).getName())
                    .add("message",entry.getValue().getText()));
    	}
    	JsonArray jsonArray = jsonArrayBuilder.build();
    	
    	JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
        		.add("action", "refreshIncompleteMessages")
                .add("usernamesAndMessages",jsonArray)
                .build();
        sendToAllUsers(addMessage);
    }
    
    public void updateIncompleteMessage(Message message, Session session) {
    	
    	if(message.getText().isEmpty()) {
    		incompleteMessages.remove(session);
    	} else {
    		incompleteMessages.put(session, message);
    	}
    	refreshIncompleteMessages();
    }
    
    public void removeIncompleteMessage(Session session) {
    	incompleteMessages.remove(session);
    	refreshIncompleteMessages();
    }
    
    public void addMessage(Message message, String username) {
    	
    	JsonObject addMessage = createMessage(username, message, "message");
        
        sendToAllUsers(addMessage);

    }
    
    public void removeMessage(Message message) {
    	
    }
    
    public void addUser(String username, Session session) {
    	users.put(session,new User(username,0,session));
    	JsonObject addMessage = createMessage(username,new Message("",0),"enter");
    	sendToAllUsers(addMessage);
    	refreshUserList();      
    }
    
    public void refreshUserList() {
    	JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    	for(User user : users.values()) {
    		jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("username", user.getName()));
    	}
    	JsonArray jsonArray = jsonArrayBuilder.build();
    	
    	JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
        		.add("action", "refreshUserList")
                .add("usernames",jsonArray)
                .build();
    	
        sendToAllUsers(addMessage);
    }
    
    public void removeUser(Session session) {
    	String username = getUser(session).getName();
    	users.remove(session);
    	incompleteMessages.remove(session);
    	JsonObject addMessage = createMessage(username,new Message("",0),"leave");
    	sendToAllUsers(addMessage);
    	removeIncompleteMessage(session);
    	refreshUserList();
    }
    
    public void sendToAllUsers(JsonObject message) {
    	for (Session session : users.keySet()) {
    		try {
				session.getBasicRemote().sendText(message.toString());
			} catch (IOException e) {
				removeUser(session);
				e.printStackTrace();
			}
        }
    }
}
