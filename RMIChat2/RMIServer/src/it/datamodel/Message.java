package it.datamodel;

import java.sql.Date;

public class Message {
//body,data,sender,receiver,gruppo
	
	private int id;
	private String body;
	private Date date;
	private int sender;
	private int receiver;
	private int group;
	

	
	public Message(String body, Date date, int sender2, int receiver2, int group) {
		this.body = body;
		this.setDate(date);
		this.sender = sender2;
		this.receiver = receiver2;
		this.group = group;
	}
	public Message(int id, String body, Date date, int sender, int receiver, int group) {
		this.id = id;
		this.body = body;
		this.setDate(date);
		this.sender = sender;
		this.receiver = receiver;
		this.group = group;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getSender() {
		return sender;
	}
	
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	public int getReceiver() {
		return receiver;
	}
	
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	
	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
	 
	
}
