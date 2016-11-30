package org.launchcode.ace.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "student")
public class Student extends User {
	
	private String address1;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;
	private String notes;
	private boolean mailList;
	private boolean emailList;
	private Date created;
	private Date updated;
//	private List<Course> courses;

	//no-arg constructor
	public Student() {}
	
//	protected void addCourse(Course course) {
//		courses.add(course);
//	}
	
	public Student(String address1, String city, String state,
			String zip, String phone, String email, String notes, boolean mailList, boolean emailList) {
		super();
		this.address1 = address1;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		this.notes = notes;
		this.mailList = mailList;
		this.emailList = emailList;
//		this.courses = new ArrayList<Course>();
	}
	


	@Column(name = "address1")
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Email(message="Please provide a valid email address")
	@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "notes")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "mail_list")
	public boolean isMailList() {
		return mailList;
	}

	public void setMailList(boolean mailList) {
		this.mailList = mailList;
	}
	
	@Column(name = "email_list")
	public boolean isEmailList() {
		return emailList;
	}

	public void setEmailList(boolean emailList) {
		this.emailList = emailList;
	}

	@CreationTimestamp
    @Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}

	@UpdateTimestamp
    @Column(name = "updated")
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	//@OneToMany
    //@JoinColumn(name = "course_uid")
//	@Column(name = "course_list")
//    public List<Course> getCourses() {
//        return this.courses;
//    }
	
//	public void setCourses(List<Course> courses) {
//		this.courses = courses;
//	}

}
