package org.launchcode.ace.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "student")
public class Student extends User {
	
	private String addressStreet;
	private String addressApt;
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
	private List<Course> courses;

	//no-arg constructor
	public Student() {}
	
	public Student(String addressStreet, String addressApt, String city, String state,
			String zip, String phone, String email, String notes, boolean mailList, boolean emailList) {
		super();
		this.addressStreet = addressStreet;
		this.addressApt = addressApt;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		this.notes = notes;
		this.mailList = mailList;
		this.emailList = emailList;
		this.courses = new ArrayList<Course>();
	}
	
	@Column(name = "addressStreet")
	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	@Column(name = "addressApt")
	public String getAddressApt() {
		return addressApt;
	}

	public void setAddressApt(String addressApt) {
		this.addressApt = addressApt;
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

	@Email(message="Please provide valid email address")
	@Pattern(regexp=".+@.+\\..+", message="That email is not valid")
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

	@ManyToMany(cascade=CascadeType.ALL, mappedBy="roster")
    public List<Course> getCourses() {
        return this.courses;
    }
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public void addCourse(Course course) {
		courses.add(course);
	}
	

	@Override
	public String toString() {
		return "Student [addressStreet=" + addressStreet + ", addressApt=" + addressApt + ", city=" + city + ", state="
				+ state + ", zip=" + zip + ", phone=" + phone + ", email=" + email + ", notes=" + notes + ", mailList="
				+ mailList + ", emailList=" + emailList + ", created=" + created + ", updated=" + updated + ", courses="
				+ courses + "]";
	}


}
