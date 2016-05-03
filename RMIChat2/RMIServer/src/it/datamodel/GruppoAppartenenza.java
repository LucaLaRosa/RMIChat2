package it.datamodel;

import java.sql.Date;

public class GruppoAppartenenza {
	
	private int group_id;
	private int user_id;
	private Date reg_date;
	
	public GruppoAppartenenza(int user_id, Date reg_date) {
		this.user_id = user_id;
		this.reg_date = reg_date;
	}
	
	public GruppoAppartenenza(int group_id, int user_id, Date reg_date) {
		this.group_id = group_id;
		this.user_id = user_id;
		this.reg_date = reg_date;
	}
	
	public int getGroup_id() {
		return group_id;
	}
	
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public Date getReg_date() {
		return reg_date;
	}
	
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	
	
}

