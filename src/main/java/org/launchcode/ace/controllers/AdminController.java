package org.launchcode.ace.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.launchcode.ace.models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	//Export Mailing List
	@RequestMapping(value="/admin/maillist", method = RequestMethod.GET)
	public String exportMailListForm() {

		return "test";
	}
	
	@RequestMapping(value="/admin/maillist", method = RequestMethod.POST)
	public String exportMailList(HttpServletResponse response, Model model) {
		List<Student> names = studentDao.findByMailListTrueOrderByLastName();
		
		try {
			mailingList.exportMailList(names, response);
			//mailingListAlt.exportMailListAlt(names, response);
			
		}
		catch(Exception e) {
			model.addAttribute("confirmMessage", "There was a problem with the export");
			System.out.println(e.getMessage());
			return "admin-main";
		}
		
		//TODO: This message is not displaying.  What is the problem???
		model.addAttribute("confirmMessage","Mailing list exported successfully");
		return "redirect:/admin/main";
	}

		
}
