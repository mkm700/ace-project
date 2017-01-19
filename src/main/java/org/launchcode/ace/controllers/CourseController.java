package org.launchcode.ace.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.launchcode.ace.models.CourseCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("course")
public class CourseController extends AbstractController {
	
	@ModelAttribute("allCourseCategories")
	public List<CourseCategory> populateCourseCategories() {
	    return courseCategoryDao.findAll();
	}
	
	//Home Page
	@RequestMapping(value = "/")
	public String homePage(Model model) {
		model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
		return "course/coursesall";
	}
	
	//Main Menu
	@RequestMapping(value = "/admin/main")
	public String adminMain(HttpServletRequest request, Model model) {
		return "admin-main";
	}
	
	//Create
	@RequestMapping(value = "/admin/course", method = RequestMethod.GET)
	public String newCourse(Model model) {
		model.addAttribute("course", new Course());
		return "course/courseform";
	}
	
    //Update
    @RequestMapping("/admin/course/edit/{uid}")
    public String editCourse(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "course/courseform";
    }
	
	//Save
	@RequestMapping(value="/admin/course", method = RequestMethod.POST)
	public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
							HttpServletRequest request) {
		
		//Validate course form fields with custom business rules
		courseValidator.validate(course, bindingResult);
		
		//Check for binding result errors
		if (bindingResult.hasErrors()) {
			return "course/courseform";
		}

		//if new course, set the Remaining Spaces equal to max students allowed
		if (request.getParameter("uid").equals("0")) {
			course.setSpacesRemain(Integer.parseInt(request.getParameter("maxStudents")));
		}
		
		//save the course to the DB
		courseDao.save(course);
		
		return "redirect:/admin/courses";
	}
	
	//Read
    @RequestMapping("/course/{uid}")
    public String showCourse(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "course/courseshow";
    }
    
    //Course List - Admin
    @RequestMapping(value = "/admin/courses", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
        return "course/courses";
    }
    
    //Course List - All
    @RequestMapping(value = "/courses/all", method = RequestMethod.GET)
    public String listAll(Model model){
        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
        return "course/coursesall";
    }
    
    //Delete
	@RequestMapping("/admin/course/delete/{uid}")
	public String delete(@PathVariable Integer uid, Model model){		
		//if no students enrolled, delete the course
		if (courseDao.findByUid(uid).getRoster().isEmpty()) {
			courseDao.delete(uid);
			return "redirect:/admin/courses";
		}
		//else display message
		else {
			model.addAttribute("courseListError", "Cannot delete a course that has students enrolled");
	        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
	        return "course/courses";
		}
	}

	//Course Roster
	@RequestMapping("/admin/course/roster/{uid}")
	public String courseRoster(@PathVariable Integer uid, Model model) {
		Course course = courseDao.findByUid(uid);
		model.addAttribute("course", course);
		model.addAttribute("students", course.getRoster());
		
		return "course/courseroster";
	}


}
