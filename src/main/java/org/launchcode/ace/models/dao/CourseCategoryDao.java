package org.launchcode.ace.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.ace.models.CourseCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CourseCategoryDao extends CrudRepository<CourseCategory, Integer> {

	CourseCategory findByUid(int uid);	
	List<CourseCategory> findAll();
	List<CourseCategory> findAllByOrderByCatName();
	
	//TODO: OrderBy?
	//http://docs.spring.io/spring-data/jpa/docs/current/reference/html/ 
	//(Example 13. Query creation from method names)
	//List<CourseCategory> findAllOrderByCatNameAsc();
}
