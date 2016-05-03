package it.datamodel;

import java.sql.Date;

public class Group {

	private int id_group;
	private Date date_creation;
	
	public Group(Date date_creation) {
		this.date_creation = date_creation;
	}

	public Group(int id_group, Date date_creation) {
		super();
		this.id_group = id_group;
		this.date_creation = date_creation;
	}
	
	public int getId_group() {
		return id_group;
	}
	
	public void setId_group(int id_group) {
		this.id_group = id_group;
	}
	
	public Date getDate_creation() {
		return date_creation;
	}
	
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}
	
}
	
	
	