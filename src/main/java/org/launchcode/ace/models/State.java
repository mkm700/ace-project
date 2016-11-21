package org.launchcode.ace.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A table containing valid semesters (season + year)
 */
//@Entity
//@Table(name = "states")
public class State extends AbstractEntity {
	
	private String stateCode;
	private String stateName;
	
	//no-arg constructor
	public State() {}
	
	public State(String stateCode, String stateName) {
		super();
		this.stateCode = stateCode;
		this.stateName = stateName;
	}

//	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
	@Column(name = "state_code")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@NotNull
    @Column(name = "state_name")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
