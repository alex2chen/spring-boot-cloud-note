/**
 * 
 */
package com.kxtx.boot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "EmployeeClaim")
public class EmployeeClaim {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employeeId;
	@Column
	@Size(min = 5, max = 30, message="error.employeeName.size")
	private String employeeName;
	@Column
	@Range(min = 18, max = 60, message="error.employee.age")
	private int employeeAge;
	@Column
	@Size(min = 5, max = 30, message="error.claimType.size")
	private String claimType;
	@Column
	@Size(min = 5, max = 30, message="error.claimDesc.size")
	private String claimDesc;
	@Column
	@Email(message="error.emailAddress.valid") @NotEmpty(message="error.emailAddress.notBlank")
	private String emailAddress;
	

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeAge() {
		return employeeAge;
	}

	public void setEmployeeAge(int employeeAge) {
		this.employeeAge = employeeAge;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getClaimDesc() {
		return claimDesc;
	}

	public void setClaimDesc(String claimDesc) {
		this.claimDesc = claimDesc;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public EmployeeClaim() {
		
	}

	public EmployeeClaim(long employeeId, String employeeName, int employeeAge, String claimType, String claimDesc,String emailAddress) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeAge = employeeAge;
		this.claimType = claimType;
		this.claimDesc = claimDesc;
		this.emailAddress = emailAddress;
	}
	
}
