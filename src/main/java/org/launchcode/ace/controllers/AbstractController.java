package org.launchcode.ace.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.User;
import org.launchcode.ace.models.dao.CourseCategoryDao;
import org.launchcode.ace.models.dao.CourseDao;
import org.launchcode.ace.models.dao.StudentDao;
import org.launchcode.ace.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {
	
	@Autowired
    protected UserDao userDao;
	
	@Autowired
    protected CourseDao courseDao;
	
	@Autowired 
	protected StudentDao studentDao;
	
	@Autowired
    protected CourseCategoryDao courseCategoryDao;
	

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
	
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
    
protected Student getStudentFromSession(HttpSession session) {
	
    	
        Integer studentId = (Integer) session.getAttribute(userSessionKey);
        return studentId == null ? null : studentDao.findByUid(studentId);
    }
    
    protected void setStudentInSession(HttpSession session, Student student) {
    	session.setAttribute(userSessionKey, student.getUid());
    }
    
    
}
