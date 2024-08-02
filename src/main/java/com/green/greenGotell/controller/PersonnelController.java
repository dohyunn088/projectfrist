package com.green.greenGotell.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.EmployeeListDTO;
import com.green.greenGotell.domain.dto.EmployeeSearchDTO;
import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.security.CustomUserDetails;
import com.green.greenGotell.service.PersonnelService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class PersonnelController {
	
	private final PersonnelService personnelService;
	

   //초기 직원조회
	@GetMapping("/personnel")
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		
		personnelService.showEmployeeList(page,model);
		
		return "/views/personnel/employee/list";
	}
	
	
	
	//원하는 직원 조회
	@PostMapping("/personnel/search")
	public String list(@RequestParam(name = "page", defaultValue = "0") int page,EmployeeSearchDTO dto, Model model ) {
		
		personnelService.showSearchEmployeeList(page,dto,model);
		
		return "views/personnel/employee/list";
	}
	
	
	//직원추가
	@PostMapping("/personnel/create")
	public String create(CreateEmployeeDTO dto) {
		personnelService.createEmployee(dto);
		
		return "redirect:/personnel";
	}
	
	
	//직원 수정폼 로드시 직원 데이터 채우기
	@GetMapping("/personnel/employee/{employeeId}")
	@ResponseBody
	public EmployeeListDTO getEmployeeData(@PathVariable(name = "employeeId") Long id) {
		EmployeeListDTO employee = personnelService.updatefindById(id);
	    return employee;
	}
	
	//직원 수정
	 @PutMapping("/personnel/update/{id}")
	   public String updateEmployee(@PathVariable(name = "id")  Long id,CreateEmployeeDTO employeeUpdateDTO) {
	       personnelService.updateEmployee(id, employeeUpdateDTO);
	       return "redirect:/personnel"; // 수정 후 직원 목록 페이지로 리다이렉트
     }
	 
	
	
	

	
	
	
	
	
}
