package org.launchcode.ace.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
@SessionAttributes("admin")
public class AdminController extends AbstractController {
	
	//Admin List
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("admins", adminDao.findAll());
        return "admins";
    }
    
    //Delete
	@RequestMapping("/admin/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    adminDao.delete(uid);
	    return "redirect:/admins";
	}

		
}
