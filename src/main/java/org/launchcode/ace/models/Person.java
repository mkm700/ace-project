package org.launchcode.ace.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Class containing relevant info about a person
 */
@Entity
@Table(name = "person")
public class Person extends AbstractEntity {
	
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
//	private State state;
	private String zip;
	private String phone;
	private String email;
	private String notes;
	private boolean mailList;
	private boolean emailList;
	private Date created;
	private Date updated;
	
	//no-arg constructor
	public Person() {}
	
	public Person(String firstName, String lastName, String address1, String city, State state,
			String zip) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = "";
		this.city = city;
//		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = "";
		this.notes = "";
		this.mailList = true;
		this.emailList = true;
		this.created = new Date();
		this.updated = new Date();
	}

	@NotNull
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@NotNull
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotNull
	@Column(name = "address1")
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "address2")
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@NotNull
	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

//	@NotNull
//	@Column(name = "state")
//	public State getState() {
//		return state;
//	}
//
//	public void setState(State state) {
//		this.state = state;
//	}

	@NotNull
	@Column(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@NotNull
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	@NotNull
	@Column(name = "mailing_list")
	public boolean isMailingList() {
		return mailList;
	}

	public void setMailingList(boolean mailingList) {
		this.mailList = mailingList;
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

}
