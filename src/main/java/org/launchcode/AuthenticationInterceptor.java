package org.launchcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.launchcode.ace.controllers.AbstractController;
import org.launchcode.ace.models.Admin;
import org.launchcode.ace.models.Student;
import org.launchcode.ace.models.User;
import org.launchcode.ace.models.dao.AdminDao;
import org.launchcode.ace.models.dao.StudentDao;
import org.launchcode.ace.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;
    
    @Autowired
    AdminDao adminDao;
    
    @Autowired
    StudentDao studentDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
							throws IOException {

        String authPageAdmin = "/admin";
        String authPageStudent = "/student";

        // Require sign-in for auth pages
        String uri = request.getRequestURI();
    	
        boolean isLoggedIn = false;
    	
        //if the page starts with an Admin authPage
        if (uri.startsWith(authPageAdmin)) {
        	Admin admin;
        	
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);

            if (userId != null) {
            	admin = adminDao.findByUid(userId);
            	
            	if (admin != null) {
            		isLoggedIn = true;
            	}
            }
        } 
        
        //else if the page starts with a student page
        else if (uri.startsWith(authPageStudent)) {
        	Student student;
        	
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);

            if (userId != null) {
            	student = studentDao.findByUid(userId);
            	
            	if (student != null) {
            		isLoggedIn = true;
            	}
            }
        }
        
        else {
        	return true;
        }

        // If user not logged in, redirect to login page
        if (!isLoggedIn) {
            response.sendRedirect("/login");
            return false;
        }
        	
        return true;
        
        
//        if ( authPages.contains(request.getRequestURI()) ) {
//
//        	boolean isLoggedIn = false;
//        	User user;
//            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);
//
//            if (userId != null) {
//            	user = userDao.findByUid(userId);
//            	
//            	if (user != null) {
//            		isLoggedIn = true;
//            	}
//            }
//
//            // If user not logged in, redirect to login page
//            if (!isLoggedIn) {
//                response.sendRedirect("/login");
//                return false;
//            }
//        }


    }

}