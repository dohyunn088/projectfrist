package com.green.greenGotell.domain.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.EmployeeListDTO;
import com.green.greenGotell.domain.dto.ProfileUpdateDTO;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;
import com.green.greenGotell.domain.enums.Role;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "employees")
@Entity
public class EmployeesEntity extends BaseEntity {
	
	//사번
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//이름
	@Column(nullable = false,columnDefinition = "varchar(20)")
	private String name;
	
	//이메일
	@Column(unique = true, nullable = false,columnDefinition = "varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String email;
	
	//비밀번호
	@Column(nullable = false,columnDefinition = "varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String pass;
	
	//전화번호
	@Column(columnDefinition = "varchar(11)")
	private String phone;
	
	//주소
	@Column(columnDefinition = "varchar(255)")
	private String address;
	
	//입사일
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate hireDate;
	
	//부서
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Department department ;
	
	//상태
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmployeeStatus employeeStatus ;
	
	//롤
	@Enumerated(EnumType.STRING) 
	@CollectionTable(name = "role"
					,joinColumns = @JoinColumn(name="employees_id"))
	@ElementCollection(fetch = FetchType.EAGER) 
	@Builder.Default 
	@Column(name = "role_name") 
	private Set<Role> roles=new HashSet<>();
	
	//Role 등록하기 위한 편의 메서드 
	public EmployeesEntity addRole(Role role) {
		roles.add(role);
		
		return this;
	}
	
	//toEmployeeDTO
	
	public EmployeeListDTO toEmployeeDTO() {
		
		
		
		return EmployeeListDTO.builder().id(id).name(name).email(email).pass(pass).phone(phone).address(address).hireDate(hireDate).department(department).employeeStatus(employeeStatus).roles(roles).build();
	}
    
	
	//마이페이지 수정 업데이트
	public EmployeesEntity update(ProfileUpdateDTO dto) {
		
		this.email=dto.getEmail();
		this.phone=dto.getPhone();
		this.address=dto.getAddress();
		
		return this;
		
	}

	// 새비밀번호 업데이트
	public EmployeesEntity update(String newpass, PasswordEncoder pe) {
		
		this.pass=pe.encode(newpass);
		
		return this;
		
		
	}
	
   //직원 수정 업데이트 
	public EmployeesEntity update(CreateEmployeeDTO employeeUpdateDTO) {
		
		this.name=employeeUpdateDTO.getName();
		this.hireDate=employeeUpdateDTO.getHireDate();
		this.department=employeeUpdateDTO.getDepartment();
		this.employeeStatus=employeeUpdateDTO.getEmployeeStatus();
		
		this.roles.clear();
		
		 String roleString = employeeUpdateDTO.getRole();
		
		switch (roleString) {
		case "CEO": this.addRole(Role.CEO);
		case "DIR":	this.addRole(Role.DIR);
		case "EMP":	this.addRole(Role.EMP);
		}
		
		
		
		return this;
	}




}
