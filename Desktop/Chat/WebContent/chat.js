
var socket;
function onMessage(event) {
	var msg = JSON.parse(event.data);
	if(msg.action == "message") {
			
		document.getElementById("messages").value = 
			document.getElementById("messages").value + "[" +
			msg.timestamp + "] " + msg.username + ": " + msg.message + "\n";
		
		scrollBottom();
	}
	if(msg.action == "enter") {
		document.getElementById("messages").value = 
			document.getElementById("messages").value + "[" +
			msg.timestamp + "] [#] " + msg.username + " has entered Nacho's chat.\n";
	
		scrollBottom();
	}
	
	if(msg.action == "leave") {
		document.getElementById("messages").value = 
			document.getElementById("messages").value + "[" +
			msg.timestamp + "] [#] " + msg.username + " has left Nacho's chat.\n";
	
		scrollBottom();
	}
	
	if(msg.action == "refreshIncompleteMessages") {
		var uam = msg.usernamesAndMessages;
		
		var im = document.getElementById("incMessages");
		if (im) {
		    while (im.firstChild) {
		      im.removeChild(im.firstChild);
		    }
		}
		
		for(var i in uam) {
			var username = uam[i].username;
			var message = uam[i].message;
			var para = document.createElement("p");
			para.className = "para";
			para.appendChild(document.createTextNode(username + " is writing: " + message));
			im.appendChild(para);	
		}	
		
	}
	
	if(msg.action == "refreshUserList") {
		
		var usernames = msg.usernames;
		
		var ul = document.getElementById("userlist");
		if (ul) {
		    while (ul.firstChild) {
		      ul.removeChild(ul.firstChild);
		    }
		}
		
		for(var i in usernames) {
			var x = usernames[i].username;
			var li = document.createElement("li");
			li.appendChild(document.createTextNode(x));
			ul.appendChild(li);	
		}		
	}
}

function scrollBottom() {
	document.getElementById("messages").scrollTop 
	= document.getElementById("messages").scrollHeight;
}

function send(event) {
	
	if (event.keyCode == 13) {
		var Message = {
			action : "addmsg",
			message : document.getElementById("msg").value
		};

		document.getElementById("msg").value = "";

		socket.send(JSON.stringify(Message));
	}
	
	var Message = { 
		action : "incompletemsg",
		message : document.getElementById("msg").value 	
	};
	socket.send(JSON.stringify(Message));
}

function enter(event) {
	
	if (event.keyCode == 13) {
		init();
	}
}

function init() {
	socket = new WebSocket("ws://" + window.location.host + "/Chat/actions/" + document.getElementById("username").value);
	socket.onmessage = onMessage;
	
	var cc = document.getElementById("chatcontainer");
	cc.style.display = 'inline';
	
	var ed = document.getElementById("enterdiv");
	ed.style.display = 'none';
	
}