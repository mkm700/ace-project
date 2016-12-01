package org.launchcode.ace.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
* Join table between Student and Course
*/
@Entity
@Table(name = "student_course")
public class StudentCourse extends AbstractEntity {
	
	//no-arg constructor
		
		public StudentCourse() {
			super();
		}

		
	
}
