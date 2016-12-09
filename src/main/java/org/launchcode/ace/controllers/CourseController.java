package org.launchcode.ace.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.launchcode.ace.models.CourseCategory;
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
@SessionAttributes("course")
public class CourseController extends AbstractController {
	
	@ModelAttribute("allCourseCategories")
	public List<CourseCategory> populateCourseCategories() {
	    return courseCategoryDao.findAll();
	}
	
	//Main Menu
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
		
		//if new course, set the Remaining Spaces equal to max students allowed
		//TODO: find a better way to do this
		if (course.getSpacesRemain() == -1) {
			course.setSpacesRemain(Integer.parseInt(maxStr));
		}
		
		//save the course to the DB
		courseDao.save(course);
		
		
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
    @RequestMapping("/course/edit/{uid}")
    public String edit(@PathVariable Integer uid, Model model){
        model.addAttribute("course", courseDao.findByUid(uid));
        return "courseform";
    }
    
    //Delete
	@RequestMapping("/course/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    courseDao.delete(uid);
	    return "redirect:/courses";
	}
	
	//Register a student
	@RequestMapping(value = "/course/register/{uid}", method = RequestMethod.GET)
	public String register(@PathVariable Integer uid, Model model, HttpServletRequest request) {
		HttpSession thisSession = request.getSession();
		User user = getUserFromSession(thisSession);
		int suid = user.getUid();
		model.addAttribute("student", studentDao.findByUid(suid));
		model.addAttribute("course", courseDao.findByUid(uid));
		return "registerform";
	}

		
	//Save Registration
	@RequestMapping(value="/course/register/{uid}", method = RequestMethod.POST)
	public String saveRegistration(HttpServletRequest request, Model model) {

		//get request parameters
		//validate parameters?
		//create new Student_Course??
		//add the student to the course roster
		//subtract 1 from remaining spaces
		//add the course to student history
		//save Student_Course??
		
		return "redirect:/coursehistory";
	}
	
	
	
	

	

}
