package org.launchcode.ace.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.ace.models.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface AdminDao extends CrudRepository<Admin, Integer> {


	Admin findByUsername(String username);
	
	Admin findByUid(int uid);
	
	List<Admin> findAll();
	
	Admin findByLastName(String lastName);
}