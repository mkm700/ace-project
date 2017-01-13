package org.launchcode.ace.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.launchcode.ace.models.Admin;
import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		//get parameters from request
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		
		//get user by their username
		//get data out of database
		User user = userDao.findByUsername(un);
		if (user == null) {
			model.addAttribute("username", un);
			model.addAttribute("error","Couldn't find that user.");
			return "login";
		}

		//check password is correct
		if (user.isMatchingPassword(pw)) {
			//log them in, if so (ie setting the user in the session)
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, user);
		}
		else {
			//display error message, reset username in form, and return to login page
			model.addAttribute("username", un);
			model.addAttribute("error","Incorrect password. Please try again.");
			return "login";
		}
		
		//redirect to page based on student or admin
		Admin admin = adminDao.findByUsername(un);
		Student student = studentDao.findByUsername(un);
		if (student != null) {
			return "redirect:/student/main/";
		}
		
		if (admin != null) {
			return "redirect:/admin/main";
		}
		
		return "error";

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
	
	//Create New Student
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newStudent(Model model) {
		model.addAttribute("student", new Student());
		return "studentform";
	}
	
	//Save Student		
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
							HttpServletRequest request, Model model) {
		
		//get parameters from request
		String un = request.getParameter("username");
		String pw = request.getParameter("pwHash");
		String verify = request.getParameter("verify");
		
		boolean isValidated = true;
		
		if (bindingResult.hasErrors() ) {
			isValidated = false;
		}
		
		String validUsername = validateUser(un);
		if (validUsername != null) {				//invalid
			isValidated = false;
			model.addAttribute("username_error", validUsername);
		}
		
		String validPassword = verifyPassword(pw, verify);
		if (validPassword != null) {
			isValidated = false;
			model.addAttribute("password_error", validPassword);
		}
		
		//if they validate and no field errors...
		if (isValidated) {
			
			//hash password and save to DB
			student.setPwHash(Student.hashPassword(pw));
			userDao.save(student);
			
			//store them in the session
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, student);
			
			return "redirect:/student/main/" + student.getUid();
		}
		
		else {
			//invalid data send back to student form with error messages
			return "studentform";
		}
		
	}
	
	//Update Password
    @RequestMapping("/student/pw/{uid}")
    //Check to see if history belongs to this user by comparing uid with user in session
    public String editPassword(@PathVariable Integer uid, Model model, HttpServletRequest request){
		if (uid == request.getSession().getAttribute(AbstractController.userSessionKey)) {
			Student test = studentDao.findByUid(uid);
			model.addAttribute("student", studentDao.findByUid(uid));
			return "changepassword";
		}
		else {
			model.addAttribute("message", "You are not authorized to update this password");
			return "error";
		}
    }
  
  //Save Password		
  	@RequestMapping(value = "/student/pw/{uid}", method = RequestMethod.POST)
  	public String savePassword(@PathVariable Integer uid, HttpServletRequest request, Model model) {
  		
  		//get parameters from request
  		String pw = request.getParameter("pwHash");
  		String verify = request.getParameter("verify");
  		
  		String validPassword = verifyPassword(pw, verify);
  		
  		if (validPassword != null) {
  		//invalid data send back to student form with error messages
  			model.addAttribute("password_error", validPassword);
  			return "changepassword";
  		}
  		
  		//Get student
  		Student student = studentDao.findByUid(uid);
		//hash password and save to DB
		student.setPwHash(Student.hashPassword(pw));
		studentDao.save(student);
  		return "redirect:/student/main/" + student.getUid();
  	}
	
	//Create New Admin
	@RequestMapping(value = "/admin/new", method = RequestMethod.GET)
	public String newAdmin(Model model) {
		model.addAttribute("admin", new Student());
		return "adminform";
	}
	
	//Save Admin		
		@RequestMapping(value = "/admin/new", method = RequestMethod.POST)
		public String saveAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult,
								HttpServletRequest request, Model model) {
			
			//get parameters from request
			String un = request.getParameter("username");
			String pw = request.getParameter("pwHash");
			String verify = request.getParameter("verify");
			
			String first = request.getParameter("firstName");
			String last = request.getParameter("lastName");
			
			boolean isValidated = true;
			
			if (bindingResult.hasErrors() ) {
				isValidated = false;
			}
			
			String validUsername = validateUser(un);
			if (validUsername != null) {				//invalid
				isValidated = false;
				model.addAttribute("username_error", validUsername);
			}
			
			String validPassword = verifyPassword(pw, verify);
			if (validPassword != null) {
				isValidated = false;
				model.addAttribute("password_error", validPassword);
			}
			
			//if they validate and no field errors...
			if (isValidated) {
				
				//hash password and save to DB
				admin.setPwHash(Admin.hashPassword(pw));
				userDao.save(admin);
				
				//store them in the session
				HttpSession thisSession = request.getSession();
				setUserInSession(thisSession, admin);
				
				return "redirect:/";
			}
			
			else {
				//invalid data send back to admin form with error messages
				return "adminform";
			}
		}

	
	public String validateUser(String un) {
		//check if username already exists		
		User checkForUser = userDao.findByUsername(un);
		if (checkForUser != null) {					//user exists
			return "That username already exists";
		}
		else {
			//validate username
			if(!User.isValidUsername(un)) {
				return "Username is not valid";
			}
		}
		return null;
	}
		
	public String verifyPassword(String pw, String verify) {
		//verify passwords are the same and valid
		if (!User.isValidPassword(pw)) {
			return "Password is not valid";
		}
		else if (!pw.equals(verify)) {
			return "Passwords do not match";
		}
		return null;
	}	
	
	
}
