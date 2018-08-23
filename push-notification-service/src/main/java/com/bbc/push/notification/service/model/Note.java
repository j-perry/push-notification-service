package com.bbc.push.notification.service.model;

public class Note {

	private String body;
	private String title;
	private String type;
	
	public Note() {
		
	}
	
	public Note(String body, String title, String type) {
		this.body = body;
		this.title = title;
		this.type = type;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
