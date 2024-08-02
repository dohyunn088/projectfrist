package com.green.greenGotell.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.green.greenGotell.domain.enums.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomUserDetailService customUserDetailService; //사용자 세부 정보를 로드하는 서비스 객체생성
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	//사용자 인증이 성공했을 때 실행되는 핸들러입니다.
	//사용자가 로그인에 성공하면 특정 로직을 수행하고, 사용자를 적절한 페이지로 리다이렉트합니다.

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				//.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (개발 환경에서만 사용 권장)
				//.csrf(Customizer.withDefaults()) // 기본적으로 CSRF 보호 활성화 (추천)
				.authorizeHttpRequests(
						authorize -> authorize
						.requestMatchers("/css/**", "/js/**", "/images/**","/webjars/**","/favicon.ico*").permitAll() // 인증 없이
						.requestMatchers("/login").permitAll() // 인증 없이 접속 가능
						
						// EMP 사용자에게 접근을 허용할 페이지
			            .requestMatchers("/").permitAll() // 홈 페이지는 모든 사용자에게 허용
			            .requestMatchers("/inventory/**", "/notices/**", "/calendar/**", "/mypage/**" ,"/status","/clock-in","/clock-out").permitAll() // EMP에게 허용할 추가 페이지들
						
						.requestMatchers("/personnel/**").hasAuthority("DEPT_HUMAN_RESOURCES") // 특정 권한을 요구하는 URL
						// 부장에게 특정 패턴에 대한 권한 부여
			            //.requestMatchers("/notices/new").hasAuthority("ROLE_" + Role.DIR.name())
						
						// CEO에게 모든 권한 부여
			            .requestMatchers("/**").hasAuthority("ROLE_" + Role.CEO.name())						
						.anyRequest().authenticated() // 위 설정을 제외한 나머지는 인증 필요 (항상 마지막에 위치)
				).formLogin(login -> login
						.loginPage("/login") // 로그인 페이지 URL
						.permitAll() // 로그인 페이지 접근 허용
						.usernameParameter("email") // 사용자 이름 파라미터 이름 설정 (기본값: username)
						.passwordParameter("pass") // 비밀번호 파라미터 이름 설정 (기본값: password)
						.successHandler(customAuthenticationSuccessHandler) // 로그인 성공 이후 처리할 내용을 정의
				).logout(logout -> logout.logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
						.invalidateHttpSession(true) // 세션 무효화
						.permitAll() // 로그아웃 URL 접근 허용
				)
				.exceptionHandling(exceptions -> exceptions
			            .accessDeniedHandler(customAccessDeniedHandler) // 커스텀 핸들러 등록
			    )
		        .userDetailsService(customUserDetailService) // 사용자 세부 정보를 로드하는 서비스 설정
		
		        .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                    .sameOrigin()
                )
            );

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(14);
	}
}