package org.launchcode.ace.models;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A table containing valid semesters (season + year)
 */
//@Entity
//@Table(name = "states")
public class State extends AbstractEntity {
	
	private String stateAbbr;
	private String stateName;
	
	//no-arg constructor
	public State() {}
	
	public State(String stateAbbr, String stateName) {
		super();
		this.stateAbbr = stateAbbr;
		this.stateName = stateName;
	}

//	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
	@Column(name = "state_abbr")
	public String getStateAbbr() {
		return stateAbbr;
	}

	public void setStateAbbr(String stateAbbr) {
		this.stateAbbr = stateAbbr;
	}

	@NotNull
	@Size(min = 2, max = 2)
    @Column(name = "state_name")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
