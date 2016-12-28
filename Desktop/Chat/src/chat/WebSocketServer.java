package chat;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions/{username}")
public class WebSocketServer {

	@Inject
	private SessionHandler sessionHandler;

	@OnOpen
	public void open(@PathParam("username") String username,
			Session session) {
		sessionHandler.addUser(username,session);
	}

	@OnClose
	public void close(Session session) {
		sessionHandler.removeUser(session);
	}

	@OnError
	public void onError(Throwable error) {
		Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {

		try (JsonReader reader = Json.createReader(new StringReader(message))) {
			JsonObject jsonMessage = reader.readObject();
			Message msg = new Message(jsonMessage.getString("message"),0);
			if ("addmsg".equals(jsonMessage.getString("action"))) {			
				sessionHandler.addMessage(msg,sessionHandler.getUser(session).getName());
			} else if("incompletemsg".equals(jsonMessage.getString("action"))) {			
				sessionHandler.updateIncompleteMessage(msg,session);			
			}
		}
	}
}
