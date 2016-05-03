package it.datamodel;

import java.sql.Date;

public class FriendShip {
	
	private int id_user1;
	private int id_user2;
	private Date creation_date;
	
	public FriendShip(int id_user2, Date creation_date) {
		this.id_user2 = id_user2;
		this.creation_date = creation_date;
	}
	
	public FriendShip(int id_user1, int id_user2, Date creation_date) {
		this.id_user1 = id_user1;
		this.id_user2 = id_user2;
		this.creation_date = creation_date;
	}
	
	public int getId_user1() {
		return id_user1;
	}
	
	public void setId_user1(int id_user1) {
		this.id_user1 = id_user1;
	}
	
	public int getId_user2() {
		return id_user2;
	}
	
	public void setId_user2(int id_user2) {
		this.id_user2 = id_user2;
	}
	
	public Date getCreation_date() {
		return creation_date;
	}
	
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	
}

