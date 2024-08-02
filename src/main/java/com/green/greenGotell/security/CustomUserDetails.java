package com.green.greenGotell.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.enums.Department;

import lombok.Getter;

@Getter	//prinsipal 에서 확인 가능, 인증정보에 prinsipal 객체에 저장
public class CustomUserDetails extends User{
	
	private static final long serialVersionUID = 1L; // 2L 버전정보를 교체
	//principal에서 확인하기 위해 추가로 등록할 수 있다.
	private Long id;
	private String email;	//=username
	private String name;	//한글이름
	private Department department;
	
	// 생성자: EmployeesEntity에서 필요한 정보를 추출하여 User 객체 생성
	public CustomUserDetails(EmployeesEntity entity) {
		super(entity.getEmail(), entity.getPass(), getAuthorities(entity)); // 상위 클래스의 생성자 호출
		
		this.email=entity.getEmail();
		this.name=entity.getName();
		this.department=entity.getDepartment();
		this.id=entity.getId();
	}
	
	// 사용자 권한을 생성하는 메소드
	private static Set<GrantedAuthority> getAuthorities(EmployeesEntity entity) {
		Set<GrantedAuthority> authorities = entity.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))// Role 객체를 SimpleGrantedAuthority로 변환
				.collect(Collectors.toSet());

		// 부서 정보를 권한에 추가
		authorities.add(new SimpleGrantedAuthority("DEPT_" + entity.getDepartment().name()));
		return authorities;
	}
	
}