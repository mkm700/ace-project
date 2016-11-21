package org.launchcode.ace.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "student")
public class Student extends User {
	
	private List<Course> courses;

	//no-arg constructor
	public Student() {}
	
	protected void addCourse(Course course) {
		courses.add(course);
	}
	
	//@OneToMany
    //@JoinColumn(name = "course_uid")
	@Column(name = "course_list")
    public List<Course> getCourses() {
        return this.courses;
    }
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
