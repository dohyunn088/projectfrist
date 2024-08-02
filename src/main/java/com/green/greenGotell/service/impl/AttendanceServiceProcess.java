package com.green.greenGotell.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.entity.AttendanceEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.repository.AttendanceEntityRepository;
import com.green.greenGotell.domain.repository.EmployeesEntityRepository;
import com.green.greenGotell.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttendanceServiceProcess implements AttendanceService {
	
	
	private final AttendanceEntityRepository   attendanceEntityRepository;
	
	private final EmployeesEntityRepository   employeesEntityRepository;
	
	
	
	
	@Override
	public void showAttendanceList(int page, Model model) {
		
		Sort sort=Sort.by(Direction.DESC,"date");
		
		Pageable pageable=PageRequest.of(page, 17, sort);
		
		Page<AttendanceEntity> employees = attendanceEntityRepository.findAll(pageable);
		
		
	   model.addAttribute("employeesAttendance", employees.getContent().stream().map(AttendanceEntity :: toAttendanceListDTO).collect(Collectors.toList()));
	   model.addAttribute("currentPage", employees.getNumber());
	   model.addAttribute("totalPages", employees.getTotalPages());
	   model.addAttribute("totalItems", employees.getTotalElements());
		
		
		
	}
	
	//페이지넘기는 처리 
	 private void addPageAttributesToModel(Page<EmployeesEntity> employees, Model model) {
	        model.addAttribute("employees", employees.getContent().stream()
	                .map(EmployeesEntity::toEmployeeDTO)
	                .collect(Collectors.toList()));
	        model.addAttribute("currentPage", employees.getNumber());
	        model.addAttribute("totalPages", employees.getTotalPages());
	        model.addAttribute("totalItems", employees.getTotalElements());
	    }

}
