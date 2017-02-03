package org.launchcode.ace.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.launchcode.ace.models.Course;
import org.launchcode.ace.models.CourseCategory;
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
@SessionAttributes("course")
public class AdminController extends AbstractController {
	
	//Main Menu
	@RequestMapping(value = "/admin/menu")
	public String adminMain(HttpServletRequest request, Model model) {
		return "admin/menu";
	}
	
	
//	//Admin List
//    @RequestMapping(value = "/admins", method = RequestMethod.GET)
//    public String list(Model model){
//        model.addAttribute("admins", adminDao.findAll());
//        return "admins";
//    }
    
    //Delete
	@RequestMapping("/admin/delete/{uid}")
	public String delete(@PathVariable Integer uid){
	    adminDao.delete(uid);
	    return "redirect:/admins";
	}
	
	//Export Mailing List
	@RequestMapping(value="/admin/maillist", method = RequestMethod.GET)
	public String exportMailListForm() {

		return "admin/exportmailing";
	}
	
//	@RequestMapping(value="/admin/maillist", method = RequestMethod.POST)
//	public void exportMailList(HttpServletResponse response, Model model) throws IOException {
//		List<Student> names = studentDao.findByMailListTrueOrderByLastName();
//		
//		//call the export method 
//		mailingList.exportMailList(names, response);
//		//mailingListAlt.exportMailListAlt(names, response);
//			
//	}
	
	@RequestMapping(value="/admin/maillist", params="clean", method = RequestMethod.POST)
	public void cleanMailList(HttpServletResponse response, Model model) throws IOException {
		List<Student> names = studentDao.findByMailListTrueOrderByLastName();
		
		//call the clean method 
		mailingList.cleanMailList(response);
			
	}
	
	@RequestMapping(value="/admin/maillist", params="export", method = RequestMethod.POST)
	public void exportMailList(HttpServletResponse response, Model model) throws IOException {
		List<Student> names = studentDao.findByMailListTrueOrderByLastName();
		
		//call the export method 
		mailingList.exportMailList(response);
		//mailingListAlt.exportMailListAlt(names, response);
			
	}

	//Temp for new template -- REMOVE LATER
    @RequestMapping(value = "/temptest", method = RequestMethod.GET)
    public String tempTest(Model model){
    	model.addAttribute("courses", courseDao.findAllByOrderByCourseCode());
        return "index-new";
    }
		
}
