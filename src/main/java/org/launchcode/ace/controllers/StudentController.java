package org.launchcode.ace.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("student")
public class StudentController extends AbstractController {
	
	//ADMIN FUNCTIONS
	//Student List
    @RequestMapping(value = "/admin/students", method = RequestMethod.GET)
    public String listStudents(Model model){
        model.addAttribute("students", studentDao.findAll());
        return "student/students";
    }
    
    //Add New student
    @RequestMapping("/admin/student")
    public String AdminNewStudent(Model model){
        model.addAttribute("student", new Student());
        return "student/studentprofile";
    }
    
    //Update Student Profile
    @RequestMapping("/admin/student/edit/{uid}")
    public String AdminEditProfile(@PathVariable Integer uid, Model model){
        model.addAttribute("student", studentDao.findByUid(uid));
        return "student/studentprofile";
    }
    
    //Save Profile		
  	@RequestMapping(value = "/admin/student", method = RequestMethod.POST)
  	public String AdminSaveProfile(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			HttpServletRequest request, Model model) {
		
		if (bindingResult.hasErrors() ) {
			//invalid data send back to student form with error messages
			return "student/studentprofile";
		}
		
  		//update the DB
		studentDao.save(student);
		
		return "redirect:/admin/students";
  	}
    
    //Delete
	@RequestMapping("/admin/student/delete/{uid}")
	public String delete(@PathVariable Integer uid, Model model){

	  //if no course history exists, delete the student
	    if (studentDao.findByUid(uid).getCourses().isEmpty()) {
			studentDao.delete(uid);
			return "redirect:/admin/students";
		}
		//else display message
		else {
			model.addAttribute("studentListError", "Cannot delete a student that has course history");
			model.addAttribute("students", studentDao.findAll());
			return "student/students";
		}	    
	}
	
	//Course History
	@RequestMapping("/admin/student/history/{uid}")
	public String courseHistory(@PathVariable Integer uid, Model model) {
		Student student = studentDao.findByUid(uid);
		model.addAttribute("student", student);
		model.addAttribute("courses", student.getCourses());
		
		return "student/coursehistory";
	}
	
	//STUDENT FUNCTIONS
	
	//Student Main Page
    @RequestMapping("/student/main")
    public String studentMain(Model model, HttpServletRequest request){
    	int uid = (int) request.getSession().getAttribute(AbstractController.userSessionKey);
        model.addAttribute("student", studentDao.findByUid(uid));
        return "student/studentmain";
    }
    
	//Update Profile
    @RequestMapping("/student/edit/{uid}")
    //Check to see if history belongs to this user by comparing uid with user in session
    public String editProfile(@PathVariable int uid, Model model, HttpServletRequest request){

		if (uid == (int) request.getSession().getAttribute(AbstractController.userSessionKey)) {
    	
			model.addAttribute("student", studentDao.findByUid(uid));
			return "student/studentprofile";
		}
		else {
			model.addAttribute("message", "You are not authorized to view this profile");
			return "error";
		}
    }
    
    //Save Profile		
  	@RequestMapping(value = "/student/edit/{uid}", method = RequestMethod.POST)
  	public String saveProfile(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			HttpServletRequest request, Model model) {
		
		if (bindingResult.hasErrors() ) {
			//invalid data send back to student form with error messages
			return "student/studentprofile";
		}
		
  		//update the DB
		studentDao.save(student);
		
		return "redirect:/student/main";
  	}
  	
  	
	//Course History (Student View)
	@RequestMapping("/student/history/{uid}")
	public String courseHistoryAdmin(@PathVariable int uid, Model model, HttpServletRequest request) {
		//Check to see if history belongs to this user by comparing uid with user in session
		if (uid == (int) request.getSession().getAttribute(AbstractController.userSessionKey)) {
		
			Student student = studentDao.findByUid(uid);
			
			model.addAttribute("student", student);
			model.addAttribute("courses", student.getCourses());
			return "student/coursehistory";
		}
		else {
			model.addAttribute("message", "You are not authorized to view this history");
			return "error";
		}	
	}
  	
	//Register a student
	@RequestMapping(value = "/student/register/{cuid}", method = RequestMethod.GET)
	public String register(@PathVariable int cuid, Model model, HttpServletRequest request) {
		HttpSession thisSession = request.getSession();
		//check if user is logged in
		//User user = getUserFromSession(thisSession);
		Student student = getStudentFromSession(thisSession);
		//if (user == null) {
		if (student == null) {
			return "redirect:/login";
		} 
		
		//check if user is already enrolled in the course
		Course course = courseDao.findByUid(cuid);
		List<Student> roster = course.getRoster();
//		if (roster.contains(user)) {
		if (roster.contains(student)) {
			model.addAttribute("course", course);
			model.addAttribute("alreadyEnrolledError", "You are already enrolled in this class.");
			return "course/courseshow";
		}

		//go to confirmation page
		//model.addAttribute("student", studentDao.findByUid(user.getUid()));
		model.addAttribute("student", studentDao.findByUid(student.getUid()));
		model.addAttribute("course", courseDao.findByUid(cuid));
	
		return "registerform";

	}
	
	//Save Registration
	@RequestMapping(value="/student/register/{uid}", method = RequestMethod.POST)
	public String saveRegistration(@PathVariable int uid, Model model, HttpServletRequest request) {

		//get student and course
		HttpSession thisSession = request.getSession();
		Student student = getStudentFromSession(thisSession);
		int sUid = student.getUid();
		Course course = courseDao.findByUid(uid);

		//add the student to the course roster, which subtracts 1 from remaining spaces
		course.addStudent(student);
		
		//add the course to student history
		student.addCourse(course);
		
		//save
		studentDao.save(student);
		courseDao.save(course);
		
		
		return "redirect:/student/history/" + sUid;
	}

	
		
}
