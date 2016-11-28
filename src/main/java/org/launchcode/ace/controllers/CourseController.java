package org.launchcode.ace.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
	
	@RequestMapping(value = "/")
	public String adminMain(HttpServletRequest request, Model model) {
		String message = request.getParameter("confirmMessage");
		System.out.println(message);
		model.addAttribute("confirmMessage",message);
		return "admin-main";
	}
	
	//Create
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public String newCourse(Model model) {
		model.addAttribute("course", new Course());
		return "courseform";
	}
	
	//Save
	@RequestMapping(value="/course", method = RequestMethod.POST)
	public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
							HttpServletRequest request, Model model) {

		boolean formErrors = false;
		//validate form input
		String feeStr = request.getParameter("fee");
		try {
			if (Float.parseFloat(feeStr) < 0) {
				model.addAttribute("feeError", "Fee must be greater than or equal to 0. ");
				formErrors = true;
			}
		}
		catch(NumberFormatException e) {
	    	e.printStackTrace();
	        model.addAttribute("feeError", "Invalid value for Fee. " );
	        formErrors = true;
		}
		
		String numClassesStr = request.getParameter("numClasses");
		try {
			if (Integer.parseInt(numClassesStr) <= 0) {
				model.addAttribute("numClassesError", "Number of Classes must be greater than 0. ");
				formErrors = true;
			}
		}
		catch(NumberFormatException e) {
	    	e.printStackTrace();
	        model.addAttribute("numClassesError", "Invalid value for Number of Classes. " );
	        formErrors = true;
		}
		
		String minStr = request.getParameter("minStudents");
		String maxStr = request.getParameter("maxStudents");

		try {
			if (Integer.parseInt(minStr) <= 0) {
				model.addAttribute("minError", "Min Students must be greater than 0. ");
				formErrors = true;
			}
		}
		catch(NumberFormatException e) {
	    	e.printStackTrace();
	        model.addAttribute("minError", "Invalid value for Min Students. " );
	        formErrors = true;
		}
		
		try {
			if (Integer.parseInt(maxStr) <= 0) {
				model.addAttribute("maxError", "Max Students must be greater than 0. ");
				formErrors = true;
			}
		}
		catch(NumberFormatException e) {
		    	e.printStackTrace();
		        model.addAttribute("maxError", "Invalid value for Max Students. " );
		        formErrors = true;
			}
		
		if (!formErrors) {
			if ( Integer.parseInt(minStr) > Integer.parseInt(maxStr) ) {
				model.addAttribute("maxError", "Max Students must be greater than or equal to Min. ");
				formErrors = true;
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = formatter.parse(request.getParameter("startDate"));
        } catch (ParseException e) {
        	e.printStackTrace();
            model.addAttribute("startError", "Enter a valid date (m/d/yyy)." );
        }
		try {
			endDate = formatter.parse(request.getParameter("endDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            model.addAttribute("endError",  "Enter a valid date (m/d/yyy)." );
        }

		if (endDate.before(startDate)) {
			model.addAttribute("endError",  "End Date must not be before Start Date" );
		}
		
		if (bindingResult.hasErrors() || formErrors) {
			return "courseform";
		}
		
		//save the course to the DB
		courseDao.save(course);
		//return "redirect:/course/" + course.getUid();
		return "redirect:/courses";
	}
	
	//Read
    @RequestMapping("/course/{uid}")
    public String showCourse(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "courseshow";
    }
    
    //Course List
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("courses", courseDao.findAll());
        return "courses";
    }
    
    //Update
    @RequestMapping("course/edit/{uid}")
    public String edit(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "courseform";
    }
    
    //Delete
	@RequestMapping("course/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    courseDao.delete(uid);
	    return "redirect:/courses";
	}



//	@RequestMapping(value = "/admin/add-course", method = RequestMethod.POST)
//	public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, Model model,
//								  RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//        	//System.out.println("BINDING ERROR: " + bindingResult + "END BINDING RESULT");
//            return "add_course";
//        }
//
//        //save the course to the DB
//		courseDao.save(course);
//		String message = " was added";
//		redirectAttributes.addAttribute("confirmMessage", message);
//        
//
//        return "redirect:/admin/main";
//    }

	
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
