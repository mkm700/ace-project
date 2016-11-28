package org.launchcode.ace.models.dao;

import java.util.List;

import org.launchcode.ace.models.CourseCategory;
import org.springframework.data.repository.CrudRepository;

public interface CourseCategoryDao extends CrudRepository<CourseCategory, Integer> {

	CourseCategory findByUid(int uid);	
	List<CourseCategory> findAll();
}
