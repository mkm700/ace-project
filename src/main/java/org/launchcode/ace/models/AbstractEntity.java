package org.launchcode.ace.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Base class for all persistent entities.
 */

@MappedSuperclass
public class AbstractEntity {

	private int uid;
	
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "uid", unique = true)
	public int getUid() {
		return this.uid;
	}
	
	protected void setUid(int uid) {
		this.uid = uid;
	}
}