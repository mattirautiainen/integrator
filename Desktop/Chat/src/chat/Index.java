package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class Index {
	
	String chatpath = "WebContent\\chat.html";
	String jschatpath = "WebContent\\chat.js";
	String csschatpath = "WebContent\\chat.css";
	
	@Path("chat")
	@GET
	@Produces("text/html")
	public InputStream getChatHTML() {
		try {
			return new FileInputStream(new File(chatpath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Path("chat.js")
	@GET
	@Produces("text/html")
	public InputStream getChatJS() {
		try {
			return new FileInputStream(new File(jschatpath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Path("chat.css")
	@GET
	@Produces("text/css")
	public InputStream getChatCSS() {
		try {
			return new FileInputStream(new File(csschatpath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
