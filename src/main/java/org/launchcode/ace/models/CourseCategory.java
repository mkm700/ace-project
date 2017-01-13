package org.launchcode.ace.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
* Class containing info about a course
*/

@Entity
@Table(name = "course_category")
public class CourseCategory extends AbstractEntity {

	private String catName;
	private String catDesc;
	private List<Course> courses;


	public CourseCategory(String catName, String catDesc) {
		super();
		this.catName = catName;
		this.catDesc = catDesc;
	}
	
	//no-arg constructor
	public CourseCategory() {}

	@NotEmpty
    @Column(name = "cat_name")
	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

    @Column(name = "cat_desc")
	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="courseCategory")
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

	@Override
	public String toString() {
		return "CourseCategory [catName=" + catName + ", catDesc=" + catDesc + ", courses=" + courses + "]";
	}
    
    
	
	

}
