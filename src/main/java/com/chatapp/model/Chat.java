package com.chatapp.model;

public class Chat {

	private String chatMessage;
	private String fromUsername;
	private String toUsername;
	private String timeStamp;

	public Chat() {		
	}


	

	public Chat(String chatMessage, String fromUsername, String toUsername, String timeStamp) {
		super();
		this.chatMessage = chatMessage;
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
		this.timeStamp = timeStamp;
	}




	public String getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

}
