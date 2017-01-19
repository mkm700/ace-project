package org.launchcode.ace.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.ace.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface StudentDao extends CrudRepository<Student, Integer> {


	Student findByUsername(String username);
	
	Student findByUid(int uid);
	List<Student> findAll();
	List<Student> findAllByOrderByLastName();
	List<Student> findByMailListTrueOrderByLastName();
	
	Student findByLastName(String lastName);
}