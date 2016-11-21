package org.launchcode.ace.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Class containing info about a course
 */
@Entity
@Table(name = "course")
public class Course extends AbstractEntity {
	
	private String courseCode;
	private String name;
	private String descShort;
	private String descLong;
	private float fee;
	private Date startDate;
	private Date endDate;
	private String startTime;
	private String endTime;
	private int numClasses;
	private int minStudents;
	private int maxStudents;
	private Date created;
	private Date updated;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
//	private Semester semester;
	
	//category
	//instructor
	//semester
	//enrolled
	//spaces remaining
	
	//no-arg constructor for Hibernate
	public Course(){}

	public Course(String courseCode, String name, String descShort, float fee,
			Date startDate, Date endDate,
			String startTime, String endTime, int numClasses, int minStudents,
			int maxStudents) {
		super();
		this.courseCode = courseCode;
		this.name = name;
		this.descShort = descShort;
		this.descLong = "";
		this.fee = fee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numClasses = numClasses;
		this.minStudents = minStudents;
		this.maxStudents = maxStudents;
		this.created = new Date();
		this.updated = new Date();
		this.sunday = false;
		this.monday = false;
		this.tuesday = false;
		this.wednesday = false;
		this.thursday = false;
		this.friday = false;
		this.saturday = false;
//		this.semester = semester;
		
	}
	@NotNull
    @Column(name = "course_code")
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@NotNull
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
    @Column(name = "desc_short")
	public String getDescShort() {
		return descShort;
	}

	public void setDescShort(String descShort) {
		this.descShort = descShort;
	}

    @Column(name = "desc_long")
	public String getDescLong() {
		return descLong;
	}

	public void setDescLong(String descLong) {
		this.descLong = descLong;
	}

	@NotNull
    @Column(name = "fee")
	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	@NotNull
    @Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
    @Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
    @Column(name = "start_time")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@NotNull
    @Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@NotNull
    @Column(name = "num_classes")
	public int getNumClasses() {
		return numClasses;
	}

	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	@NotNull
    @Column(name = "min_students")
	public int getMinStudents() {
		return minStudents;
	}

	public void setMinStudents(int minStudents) {
		this.minStudents = minStudents;
	}

	@NotNull
    @Column(name = "max_students")
	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	@NotNull
    @Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}

	@NotNull
    @Column(name = "updated")
	public Date getUpdated() {
		return updated;
	}
	
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	//@ManyToOne
//	@Column(name = "semester")
//	public Semester getSemester() {
//		return semester;
//	}

//	public void setSemester() {
//		this.semester = semester;
//	}


	
	
	
	
}
