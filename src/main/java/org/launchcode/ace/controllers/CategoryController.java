package org.launchcode.ace.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
@SessionAttributes("courseCategory")
public class CategoryController extends AbstractController {
	
		//Create
		@RequestMapping(value = "/admin/category/new", method = RequestMethod.GET)
		public String newCategory(Model model) {
			model.addAttribute("courseCategory", new CourseCategory());
			return "admin/category/categoryform";
		}
		
		//Save
		@RequestMapping(value="/admin/category", method = RequestMethod.POST)
		public String saveCourse(@Valid @ModelAttribute("courseCategory") CourseCategory courseCategory, BindingResult bindingResult,
								HttpServletRequest request, Model model) {
	
			if (bindingResult.hasErrors()) {
				return "admin/category/categoryform";
			}
			
			//save the course to the DB
			courseCategoryDao.save(courseCategory);

			return "redirect:/admin/category/list";
		}
		
	    //Category List
	    @RequestMapping(value = "/admin/category/list", method = RequestMethod.GET)
	    public String list(Model model){
	    	model.addAttribute("courseCategories", courseCategoryDao.findAllByOrderByCatName());
	        return "admin/category/list";
	    }
	    
	    //Update
	    @RequestMapping("/admin/category/edit/{uid}")
	    public String edit(@PathVariable Integer uid, Model model, HttpServletRequest request){
	        model.addAttribute("courseCategory", courseCategoryDao.findByUid(uid));
	        return "admin/category/categoryform";
	    }
	    
	    //Delete
		@RequestMapping("/admin/category/delete/{uid}")
		public String delete(@PathVariable Integer uid){
		    courseCategoryDao.delete(uid);
		    return "redirect:/admin/category/list";
		}

}
