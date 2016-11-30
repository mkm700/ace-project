package org.launchcode.ace.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("student")
public class StudentController extends AbstractController {
	
	//Category List
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("students", studentDao.findAll());
        return "students";
    }
    
    //Update
    @RequestMapping("student/edit/{uid}")
    public String edit(@PathVariable Integer uid, Model model, HttpServletRequest request){
        model.addAttribute("student", studentDao.findByUid(uid));
        return "studentform";
    }
    
    //Delete
	@RequestMapping("student/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    studentDao.delete(uid);
	    return "redirect:/students";
	}

		
		
}
