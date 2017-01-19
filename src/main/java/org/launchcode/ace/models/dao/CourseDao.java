package org.launchcode.ace.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.ace.models.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

	@Transactional
	@Repository
	public interface CourseDao extends CrudRepository<Course, Integer> {
	    
		Course findByUid(int uid);
	    List<Course> findAll();
	    List<Course> findAllByOrderByCourseCode();
	    
	    //method signatures
	    
	}

