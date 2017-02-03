package org.launchcode.ace.models;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "admin")
public class Admin extends User {
	
	//no-arg constructor	
	public Admin() {
		super();
	}


	
}

