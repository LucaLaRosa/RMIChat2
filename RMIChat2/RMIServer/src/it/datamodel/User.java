package it.datamodel;

import java.sql.Date;

public class User {
	private int id;
	private String name;
	private String pass;
	private String status_message;
	private Date date_creation;
	
	
	
	public User() {
		this.name = null;
		this.pass = null;
		this.status_message = null;
		this.date_creation = null;
		
	}

	public User(int id, String name, String pass, String status_message, Date date_creation) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.status_message = null;
		this.date_creation = date_creation;
	}
	
	public boolean isMessageSet() {
		return (this.status_message==null);
		
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getStatus_message() {
		return status_message;
	}
	
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	
	public Date getDate_creation() {
		return date_creation;
	}
	
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	
}