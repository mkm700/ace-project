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
	
	//Home Page
	@RequestMapping(value = "/")
	public String homePage(Model model) {
		model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
		return "course/list";
	}
	
    //List
    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    public String listCourses(Model model){
        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
        return "course/list";
    }
    
	//Detail
    @RequestMapping("/course/detail/{uid}")
    public String courseDetail(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "course/detail";
    }
    
//************ADMIN SECTION************
    //Admin: Course List / Menu
    @RequestMapping(value = "/admin/course/list", method = RequestMethod.GET)
    public String courseList(Model model){
        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
        return "admin/course/list";
    }
	
	//Admin: New Course
	@RequestMapping("/admin/course/new")
	public String newCourseC(Model model) {
		model.addAttribute("course", new Course());
		model.addAttribute("pageTitle", "New Course");
		return "admin/course/courseform";
	}
	
    //Admin: Update
    @RequestMapping("/admin/course/edit/{uid}")
    public String editCourse(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        model.addAttribute("pageTitle", "Edit Course");
        return "admin/course/courseform";
    }
    
	//Admin: Course Categories drop down list
	@ModelAttribute("allCourseCategories")
	public List<CourseCategory> populateCourseCategories() {
	    return courseCategoryDao.findAll();
	}
	
	//Admin: Save
	@RequestMapping(value="/admin/course", method = RequestMethod.POST)
	public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
							HttpServletRequest request) {
		
		//Validate course form fields with custom business rules
		courseValidator.validate(course, bindingResult);
		
		//Check for binding result errors
		if (bindingResult.hasErrors()) {
			return "admin/course/courseform";
		}

		//if new course, set the Remaining Spaces equal to max students allowed
		if (request.getParameter("uid").equals("0")) {
			course.setSpacesRemain(Integer.parseInt(request.getParameter("maxStudents")));
		}
		
		//save the course to the DB
		courseDao.save(course);
		
		return "redirect:/admin/course/list";
	}
    
    //Admin: Delete
	@RequestMapping("/admin/course/delete/{uid}")
	public String delete(@PathVariable Integer uid, Model model){		
		//if no students enrolled, delete the course
		if (courseDao.findByUid(uid).getRoster().isEmpty()) {
			courseDao.delete(uid);
			return "redirect:/admin/course/list";
		}
		//else display message
		else {
			model.addAttribute("courseListError", "Cannot delete a course that has students enrolled");
	        model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
	        return "admin/course/list";
		}
	}

	//Admin: Course Roster
	@RequestMapping("/admin/course/roster/{uid}")
	public String courseRoster(@PathVariable Integer uid, Model model) {
		Course course = courseDao.findByUid(uid);
		model.addAttribute("course", course);
		model.addAttribute("students", course.getRoster());
		
		return "admin/course/courseroster";
	}
	
    //Admin: Report - Filled Courses
    @RequestMapping(value = "/admin/reports/course/filled", method = RequestMethod.GET)
    public String courseFilled(Model model){
        //model.addAttribute("courses", courseDao.findByCourseCodeStartingWith("CO"));
        model.addAttribute("reportTitle","Course Enrollment - Filled");
        return "admin/course/reportenroll";
    }
	
}
