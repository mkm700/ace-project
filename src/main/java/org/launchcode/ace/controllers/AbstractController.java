package org.launchcode.ace.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.ace.models.User;
import org.launchcode.ace.models.dao.CourseDao;
import org.launchcode.ace.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public abstract class AbstractController {
	
	@Autowired
    protected UserDao userDao;
	
	@Autowired
    protected CourseDao courseDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
}
