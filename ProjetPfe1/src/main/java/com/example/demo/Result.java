package com.example.demo;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable{
	
	private Long revision;
	private String autheur;
	private Date date;
	private String message;
	public Long getRevision() {
		return revision;
	}
	public void setRevision(Long revision) {
		this.revision = revision;
	}
	public String getAutheur() {
		return autheur;
	}
	public void setAutheur(String autheur) {
		this.autheur = autheur;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Result(Long revision, String autheur, Date date, String message) {
		super();
		this.revision = revision;
		this.autheur = autheur;
		this.date = date;
		this.message = message;
	}
	
	
	
}
