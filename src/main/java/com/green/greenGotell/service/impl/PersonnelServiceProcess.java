package com.green.greenGotell.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.EmployeeListDTO;
import com.green.greenGotell.domain.dto.EmployeeSearchDTO;
import com.green.greenGotell.domain.entity.EmployeePhotoEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.repository.EmployeePhotoEntityRepository;
import com.green.greenGotell.domain.repository.EmployeesEntityRepository;
import com.green.greenGotell.service.PersonnelService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PersonnelServiceProcess implements PersonnelService {

	private final EmployeesEntityRepository rep;
	private final EmployeePhotoEntityRepository employeePhotoEntityRep;
	private final PasswordEncoder pe;
	
	//직원가입
	@Override
	public void createEmployee(CreateEmployeeDTO dto) {
		
		 EmployeesEntity employee=rep.save(dto.toEntity(pe));
		 
	    // 프로필 엔티티 생성 및 저장
	     EmployeePhotoEntity photoEntity = EmployeePhotoEntity.builder()
	                .employee(employee) // 저장된 직원 엔티티 사용 자동으로 id값 매핑
	                .fileContent(new byte[0]) // 초기에는 null할당
	                .build();

	        employeePhotoEntityRep.save(photoEntity);
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
	
	
    //페이지 로드시 전체 직원정보 
	@Override
	public void showEmployeeList(int page,Model model) {
		
		Sort sort=Sort.by(Direction.DESC,"id");
		
		Pageable pageable=PageRequest.of(page, 17, sort);
		
		Page<EmployeesEntity> employees = rep.findAll(pageable);
		
		model.addAttribute("employees", employees.getContent().stream().map(EmployeesEntity :: toEmployeeDTO).collect(Collectors.toList()));
	    model.addAttribute("currentPage", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());
		
	}

    //원하는 직원 정보 
	@Override
	public void showSearchEmployeeList(int page, EmployeeSearchDTO dto, Model model) {
        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 17, sort);

        Page<EmployeesEntity> employees;

        if (dto.isEmpty()) {
            // 검색 조건이 없으면 전체 목록 조회
            employees = rep.findAll(pageable);
        } else {
            // 검색 조건이 있으면 조건에 맞는 직원 조회
            employees = rep.findBySearchEmployee(
                dto.getDepartment(),
                dto.getEmployeeStatus(),
                dto.getName(),
                pageable
            );
        }

        model.addAttribute("employees", employees.getContent().stream()
                .map(EmployeesEntity::toEmployeeDTO)
                .collect(Collectors.toList()));
        
        // 페이징 정보도 모델에 추가
        model.addAttribute("currentPage", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());

    }

   //수정버튼클릭시 보여줄 직원 정보 전송
	@Override
	public EmployeeListDTO updatefindById(Long id) {
		
		  EmployeeListDTO employee = rep.findById(id).orElseThrow().toEmployeeDTO();
		  
		  employee.setHighestRole(employee.getHighRole());
		
		return employee;
	}

	// 직원 정보 수정
	@Transactional
	@Override
	public void updateEmployee(Long id, CreateEmployeeDTO employeeUpdateDTO) {
		
		
		 EmployeesEntity employee=rep.findById(id).orElseThrow().update(employeeUpdateDTO);
				 
				  
		
	}
	

	


}
