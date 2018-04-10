package com.example.demo;

import java.io.Serializable;
import java.util.List;

import com.offbytwo.jenkins.model.Build;

public class Jenkinsresult implements Serializable {
	private String name ;
	private String build;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public Jenkinsresult(String name, String build) {
		super();
		this.name = name;
		this.build = build;
	}
	public Jenkinsresult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
