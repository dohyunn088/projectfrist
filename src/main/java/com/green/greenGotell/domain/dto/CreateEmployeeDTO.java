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


@Getter
@Setter
public class CreateEmployeeDTO {
	
	private String name;
	private String email;
	private String pass;
	private Department department ;
	private String role;
	private LocalDate hireDate;
	private EmployeeStatus employeeStatus ;
	
	
	public EmployeesEntity toEntity(PasswordEncoder pe) {
		// TODO Auto-generated method stub
	
		
		EmployeesEntity entity = EmployeesEntity.builder().name(name).email(email).pass(pe.encode(pass)).department(department)
				                                          .hireDate(hireDate).employeeStatus(employeeStatus).build();
		
		switch (role) {
		case "CEO": entity.addRole(Role.CEO);
		case "DIR":	entity.addRole(Role.DIR);
		case "EMP":	entity.addRole(Role.EMP);
		}
		
		return entity;
	}
	

}
