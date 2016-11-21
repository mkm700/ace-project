package org.launchcode.ace.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A table containing valid semesters (season + year)
 */
//@Entity
//@Table(name = "semester")
public class Semester extends AbstractEntity {
	
	private String semester;
	
	//no-arg constructor
	public Semester() {}

	public Semester(String semester) {
		super();
		this.semester = semester;
	}

	//@OneToMany(mappedBy = "course_uid", cascade = CascadeType.ALL)
	@Column(name="semester")
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	
	
}
