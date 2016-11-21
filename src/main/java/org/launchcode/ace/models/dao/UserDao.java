package org.launchcode.ace.models.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.launchcode.ace.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

	@Transactional
	@Repository
	public interface UserDao extends CrudRepository<User, Integer> {

	    User findByUid(int uid);
	    
	    List<User> findAll();
	    
	    //method signatures
	    User findByUsername(String username);
	}

