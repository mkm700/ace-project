package org.launchcode.ace.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

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
	private CourseCategory courseCategory;
	private float fee;
	private Date startDate;
	private Date endDate;
	private String startTime;
	private String endTime;
	private int numClasses;
	private int minStudents;
	private int maxStudents;
	private int spacesRemain;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private String imageFileName;
	private Date created;
	private Date updated;
	private List<Student> roster;	
	
	public Course(String courseCode, String name, String descShort, String descLong, CourseCategory courseCategory, 
			float fee, Date startDate, Date endDate, String startTime, String endTime, int numClasses, 
			int minStudents, int maxStudents, boolean sunday, boolean monday, boolean tuesday, 
			boolean wednesday, boolean thursday, boolean friday, boolean saturday, String imageFileName) {
	super();
	this.courseCode = courseCode;
	this.name = name;
	this.descShort = descShort;
	this.descLong = descLong;
	this.courseCategory = courseCategory;
	this.fee = fee;
	this.startDate = startDate;
	this.endDate = endDate;
	this.startTime = startTime;
	this.endTime = endTime;
	this.numClasses = numClasses;
	this.minStudents = minStudents;
	this.maxStudents = maxStudents;
	this.spacesRemain = maxStudents;
	this.sunday = sunday;
	this.monday = monday;
	this.tuesday = tuesday;
	this.wednesday = wednesday;
	this.thursday = thursday;
	this.friday = friday;
	this.saturday = saturday;
	this.imageFileName = imageFileName;
	this.roster = new ArrayList<Student>();
}

	//no-arg constructor for Hibernate
	public Course() {}	
	
	@NotEmpty
    @Column(name = "course_code")
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@NotEmpty
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotEmpty
    @Column(name = "desc_short")
    @Size(max = 1200)
	public String getDescShort() {
		return descShort;
	}

	public void setDescShort(String descShort) {
		this.descShort = descShort;
	}

    @Column(name = "desc_long")
    @Size(max = 1200)
	public String getDescLong() {
		return descLong;
	}

	public void setDescLong(String descLong) {
		this.descLong = descLong;
	}

    @Column(name = "fee")
    @Min(value=0)
	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	public CourseCategory getCourseCategory() {
		return courseCategory;
	}
	
	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

    @Column(name = "start_date")
    @DateTimeFormat(pattern="M/d/yyyy")
    @NotNull
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

    @Column(name = "end_date")
    @DateTimeFormat(pattern="M/d/yyyy")
    @NotNull
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    @Column(name = "start_time")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

    @Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

    @Column(name = "num_classes")
    @Min(value=1)
	public int getNumClasses() {
		return numClasses;
	}

	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}
	
    @Column(name = "min_students")
    @Min(value=1)
	public int getMinStudents() {
		return minStudents;
	}

	public void setMinStudents(int minStudents) {
		this.minStudents = minStudents;
	}

    @Column(name = "max_students")
    @Min(value=1)
	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	
	@Column(name = "spaces_remain")
	public int getSpacesRemain() {
		return spacesRemain;
	}

	public void setSpacesRemain(int spacesRemain) {
		this.spacesRemain = spacesRemain;
	}

    @Column(name = "sunday")
	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}
    @Column(name = "monday")
	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

    @Column(name = "tuesday")
	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

    @Column(name = "wednesday")
	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

    @Column(name = "thursday")
	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

    @Column(name = "friday")
	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

    @Column(name = "saturday")
	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}
	
    @Column(name = "image_file_name")
	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
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
	
    @ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="student_course", joinColumns=@JoinColumn(name="course.uid"), inverseJoinColumns=@JoinColumn(name="student.uid"))  
    public List<Student> getRoster()  
    {  
        return this.roster;  
    }  
    
    public void setRoster(List<Student> roster)  
    {  
        this.roster = roster;
    }  
    
	public void addStudent(Student student) {
		roster.add(student);
		this.spacesRemain = this.spacesRemain - 1;
	}

	@Override
	public String toString() {
		return "Course [courseCode=" + courseCode + ", name=" + name + ", descShort=" + descShort + ", descLong="
				+ descLong + ", fee=" + fee + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", numClasses=" + numClasses + ", minStudents=" + minStudents
				+ ", maxStudents=" + maxStudents + ", sunday=" + sunday + ", monday=" + monday + ", tuesday=" + tuesday
				+ ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday + ", saturday="
				+ saturday + ", created=" + created + ", updated=" + updated + "]";
	}
	
	
	
	
}
