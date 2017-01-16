package org.launchcode.ace.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.launchcode.ace.models.Course;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CourseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Course.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Course course = (Course)target;
		
		//Max students can't be less than min students
		int min = course.getMinStudents();
		int max = course.getMaxStudents();
		
		if (course.getMaxStudents() < course.getMinStudents()) {
			errors.rejectValue("maxStudents", "course.maxStudents.tooSmall");
		}
		
		//Check for valid start and end dates
		Date start = course.getStartDate();
		Date end = course.getEndDate();
		if (start != null && end != null) {
			if (end.before(start)) {
				errors.rejectValue("endDate", "course.endDate.after");
			}
		}
		
	}
	
	

	
}
