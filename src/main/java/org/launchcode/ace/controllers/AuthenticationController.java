package org.launchcode.ace.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	//Create New Student User
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String newStudent(Model model) {
		model.addAttribute("student", new Student());
		return "studentform";
	}

	//Save Student User		
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
							HttpServletRequest request, Model model) {
		
		//get parameters from request
		String un = request.getParameter("username");
		String pw = request.getParameter("pwHash");
		String verify = request.getParameter("verify");
		
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String addr = request.getParameter("address1");
		String email = request.getParameter("email");
		
		boolean isValidated = true;
		
		if (bindingResult.hasErrors() ) {
			isValidated = false;
		}
	
		//check if username already exists
		User checkForUser = userDao.findByUsername(un);
		if (checkForUser != null) {					//user exists
			model.addAttribute("username_error", "That username already exists");
			isValidated = false;
		}
		else {
			//validate username and verify passwords are the same and valid
			if(!User.isValidUsername(un)) {
				model.addAttribute("username_error", "Username is not valid");
				isValidated = false;
			}
		}
	
		if (!User.isValidPassword(pw)) {
			model.addAttribute("password_error", "Password is not valid");
			isValidated = false;
		}
		else if (!pw.equals(verify)) {
			model.addAttribute("verify_error", "Passwords do not match");
			isValidated = false;
		}
		
		//if they validate and no field errors...
		if (isValidated) {
			
			//hash password and save to DB
			//TODO: should this be changed to user?
			student.setPwHash(pw);
			userDao.save(student);
			
			//store them in the session
			HttpSession thisSession = request.getSession();
			setUserInSession(thisSession, student);
			
			return "redirect:/courses";
		}
		
		else {
			//invalid data send back to student form with error messages
			return "studentform";
		}
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
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
		
		return "redirect:/student/coursehistory";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
