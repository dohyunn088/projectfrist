package com.green.greenGotell.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.repository.EmployeesEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 포함한 모든 필드를 초기화하는 생성자를 자동으로 생성합니다.
@Service // 이 클래스를 Spring의 서비스 빈으로 등록합니다.
public class CustomUserDetailService implements UserDetailsService {
	
	private final EmployeesEntityRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		EmployeesEntity employee = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return new CustomUserDetails(employee);
    }
}
