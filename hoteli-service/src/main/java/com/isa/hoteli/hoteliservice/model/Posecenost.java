package com.isa.hoteli.hoteliservice.model;

import java.sql.Date;

public class Posecenost {
	
	private Long id;
	private Date date;
	
	public Posecenost() {

	}
	
	public Posecenost(Long id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
