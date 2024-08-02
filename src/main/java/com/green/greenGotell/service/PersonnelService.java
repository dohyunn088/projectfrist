package com.green.greenGotell.service;

import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.EmployeeListDTO;
import com.green.greenGotell.domain.dto.EmployeeSearchDTO;
import com.green.greenGotell.security.CustomUserDetails;

public interface PersonnelService{

	void createEmployee(CreateEmployeeDTO dto);

	void showEmployeeList(int page,Model model);

	void showSearchEmployeeList(int page,EmployeeSearchDTO dto, Model model);

	EmployeeListDTO updatefindById(Long id);

	void updateEmployee(Long id, CreateEmployeeDTO employeeUpdateDTO);

	

}
