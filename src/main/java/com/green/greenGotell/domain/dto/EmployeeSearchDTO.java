package com.green.greenGotell.domain.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;
import com.green.greenGotell.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Setter
@Getter
public class EmployeeSearchDTO {
	
	private String name;
	private Department department ;
	private EmployeeStatus employeeStatus ;
	
	
	 public boolean isEmpty() {
	        return department == null && employeeStatus == null && (name == null || name.trim().isEmpty());
	    }
	
	

	

}
