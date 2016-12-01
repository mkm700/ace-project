package org.launchcode.ace.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.launchcode.ace.models.CourseCategory;
import org.launchcode.ace.models.Student;
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
	
	//Student List
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("students", studentDao.findAll());
        return "students";
    }
    
    //Update Profile
    @RequestMapping("/student/edit/{uid}")
    public String edit(@PathVariable Integer uid, Model model){
        model.addAttribute("student", studentDao.findByUid(uid));
        return "studentprofile";
    }
    
    //Save Profile		
  	@RequestMapping(value = "/student/edit/{uid}", method = RequestMethod.POST)
  	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			HttpServletRequest request, Model model) {
		
  		//update the DB
		studentDao.save(student);
		
		return "redirect:/students";
  	}
    
    //Delete
	@RequestMapping("/student/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    studentDao.delete(uid);
	    return "redirect:/students";
	}

		
	//Course History
	@RequestMapping("/student/history/{uid}")
	public String courseHistory(@PathVariable Integer uid, Model model) {
		Student student = studentDao.findByUid(uid);
		model.addAttribute("student", student);
		model.addAttribute("courses", student.getCourses());
		
		return "coursehistory";
	}
		
}
