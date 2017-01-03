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
	
	//ADMIN
	//Student List
    @RequestMapping(value = "/admin/students", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("students", studentDao.findAll());
        return "students";
    }
    
    //Update Profile
    @RequestMapping("/admin/student/edit/{uid}")
    public String edit(@PathVariable Integer uid, Model model){
        model.addAttribute("student", studentDao.findByUid(uid));
        return "studentprofile";
    }
    
    //Save Profile		
  	@RequestMapping(value = "/admin/student/edit/{uid}", method = RequestMethod.POST)
  	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			HttpServletRequest request, Model model) {
		
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
			return "students";
		}	    
	    
	}
	
	//STUDENT
	//Register a student
	@RequestMapping(value = "/student/register/{cuid}", method = RequestMethod.GET)
	public String register(@PathVariable Integer cuid, Model model, HttpServletRequest request) {
		HttpSession thisSession = request.getSession();
		//check if user is logged in
		User user = getUserFromSession(thisSession);
		if (user == null) {
			return "redirect:/login";
		} 
		
		//check if user is already enrolled in the course
		Course course = courseDao.findByUid(cuid);
		List<Student> students = course.getStudents();
		if (students.contains(user)) {
			model.addAttribute("course", course);
			model.addAttribute("alreadyEnrolledError", "You are already enrolled in this class.");
			return "courseshow";
		}

		//go to confirmation page
		model.addAttribute("student", studentDao.findByUid(user.getUid()));
		model.addAttribute("course", courseDao.findByUid(cuid));
	
		return "registerform";

	}
	
	//Save Registration
	@RequestMapping(value="/student/register/{uid}", method = RequestMethod.POST)
	public String saveRegistration(@PathVariable Integer uid, Model model, HttpServletRequest request) {

		//get student and course
		HttpSession thisSession = request.getSession();
		Student student = getStudentFromSession(thisSession);
		int sUid = student.getUid();
		Course course = courseDao.findByUid(uid);

		//add the student to the course roster, subtract 1 from remaining spaces
		course.addStudent(student);
		
		//add the course to student history
		student.addCourse(course);
		
		//save
		studentDao.save(student);
		courseDao.save(course);
		
		//save Student_Course??
		
		return "redirect:/student/history/" + sUid;
	}
	
		
	//Course History (Admin view)
	@RequestMapping("/admin/student/history/{uid}")
	public String courseHistory(@PathVariable Integer uid, Model model) {
		Student student = studentDao.findByUid(uid);
		model.addAttribute("student", student);
		model.addAttribute("courses", student.getCourses());
		
		return "coursehistory";
	}
	
	//Course History (Student View)
	@RequestMapping("/student/history/{uid}")
	public String courseHistoryAdmin(@PathVariable Integer uid, Model model, HttpServletRequest request) {
		//Check to see if history belongs to this user by comparing uid with user in session
		if (uid == request.getSession().getAttribute(AbstractController.userSessionKey)) {
		
			Student student = studentDao.findByUid(uid);
			
			model.addAttribute("student", student);
			model.addAttribute("courses", student.getCourses());
			return "coursehistory";
		}
		else {
			model.addAttribute("message", "You are not authorized to view this history");
			return "error";
		}
		
	}
		
}
