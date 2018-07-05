package com.chatapp.model;

public class Chat {

	private String chatMessage;
	private String fromUsername;
	private String toUsername;

	public Chat() {		
	}

	public Chat(String chatMessage, String fromUsername, String toUsername) {
		super();
		this.chatMessage = chatMessage;
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
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
