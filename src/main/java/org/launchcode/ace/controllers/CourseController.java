package org.launchcode.ace.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("course")
public class CourseController extends AbstractController {
	
	@RequestMapping(value = "/admin/main")
	public String adminMain(HttpServletRequest request, Model model) {
		String message = request.getParameter("confirmMessage");
		System.out.println(message);
		model.addAttribute("confirmMessage",message);
		return "admin-main";
	}
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		// fetch courses and pass to template
		List<Course> courses = courseDao.findAll();
		model.addAttribute("courses", courses);
		
		return "index";
	}
	
	@RequestMapping(value = "/admin/add-course", method = RequestMethod.GET)
	public String addCourse(Course course, Model model) {
		model.addAttribute("course", new Course());
		return "add_course";
	}
	

	@RequestMapping(value = "/admin/add-course", method = RequestMethod.POST)
	public String checkCourseInfo(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, Model model,
								  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
        	//System.out.println("BINDING ERROR: " + bindingResult + "END BINDING RESULT");
            return "add_course";
        }

        //save the course to the DB
		courseDao.save(course);
		String message = " was added";
		redirectAttributes.addAttribute("confirmMessage", message);
        

        return "redirect:/admin/main";
    }
	
//	@RequestMapping(value = "/admin/add-course", method = RequestMethod.POST)
//	public String addCourse(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
//		//get request parameters
//			String courseCode = request.getParameter("courseCode");
//			String name = request.getParameter("name");
//			String descShort = request.getParameter("descShort");
//			float fee = Float.valueOf(request.getParameter("fee"));
//			
//			//validate fields
//			//required parameters
//			boolean isError = false;
//			
//			if (courseCode.isEmpty()) {
//				model.addAttribute("errorCourseCode", "Course Code is required");
//				isError = true;
//			}
//			
//			if (name.isEmpty()) {
//				model.addAttribute("errorName", "Course Name is required");
//				isError = true;
//			}
//			
//			if (descShort.isEmpty()) {
//				model.addAttribute("errorDescShort", "Short Description is required");
//				isError = true;
//			}
//			
////			String sFee = request.getParameter("fee");
////			float fee = 0;
////			try {
////				fee = Float.parseFloat(sFee);
////			}
//			
//			//if valiadtion problem(s), set field values and return to form
//			if (isError) {
//				model.addAttribute("courseCode", courseCode);
//				model.addAttribute("name", name);
//				model.addAttribute("descShort", descShort);
//				model.addAttribute("fee", fee);
//				return "add-course";
//			}
//			
//			//get the user and username
////			HttpSession thisSession = request.getSession();
////			User author = getUserFromSession(thisSession);
//			
//			//create new Course, save required fields to DB, update optional fields
//			Course course = new Course(courseCode, name, descShort);
//
//				courseDao.save(course);
//				String message = courseCode + " was added";
//				redirectAttributes.addAttribute("confirmMessage", message);
//
//			
//			// redirect to admin main menu
//			return "redirect:/admin/main";
//	}
	
	
	

}
