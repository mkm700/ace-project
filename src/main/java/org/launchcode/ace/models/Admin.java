package org.launchcode.ace.models;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "admin")
public class Admin extends User {
	
	private String department;
	
	//no-arg constructor	
	public Admin() {
		super();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
}

