package org.launchcode.ace.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "user")
public abstract class User extends AbstractEntity {
	
	private String username;
	private String pwHash;
	private String firstName;
	private String lastName;
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	//no-arg constructor
	public User() {}
	
	public User(String username, String password, String firstName, String lastName) {
		
		super();
		
//		if (!isValidUsername(username)) {
//			throw new IllegalArgumentException("Invalid username");
//		}
		
		this.username = username;
		this.pwHash = password;
		this.firstName = firstName;
		this.lastName = lastName;
		
		
	}
		
	@NotNull
    @Column(name = "pw_hash")
	public String getPwHash() {
		return pwHash;
	}
	
	//@SuppressWarnings("unused")
	public void setPwHash(String password) {
		this.pwHash = password;
	}
	
	@NotNull
    @Column(name = "username", unique = true)
	public String getUsername() {
		return this.username;
	}
	
	public static String hashPassword(String password) {
		return encoder.encode(password);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotEmpty
	@Column(name="first_name")
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotEmpty
	@Column(name="last_name")
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	//checks that the given pw is correct for the user
	//user.isMatchingPassword(...)
	public boolean isMatchingPassword(String password) {
		System.out.println(pwHash);
		System.out.println(password);
		return encoder.matches(password, pwHash);
	}
	
	//checks that the pw meets minimum standards
	public static boolean isValidPassword(String password) {
		Pattern validUsernamePattern = Pattern.compile("(\\S){6,20}");
		Matcher matcher = validUsernamePattern.matcher(password);
		return matcher.matches();
	}
	
	//checks that username meets min standards
	public static boolean isValidUsername(String username) {
		Pattern validUsernamePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{4,11}");
		Matcher matcher = validUsernamePattern.matcher(username);
		return matcher.matches();
	}

}
