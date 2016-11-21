package org.launchcode.ace.controllers;

import java.util.List;

import org.launchcode.ace.models.Course;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CourseController extends AbstractController {
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		// fetch courses and pass to template
		List<Course> courses = courseDao.findAll();
		model.addAttribute("courses", courses);
		
		return "index";
	}

}
