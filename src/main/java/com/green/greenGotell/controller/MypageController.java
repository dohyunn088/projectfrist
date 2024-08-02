package com.green.greenGotell.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.ProfileUpdateDTO;
import com.green.greenGotell.security.CustomUserDetails;
import com.green.greenGotell.service.MypageService;
import com.green.greenGotell.service.PersonnelService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RequiredArgsConstructor
@Controller
public class MypageController {
	
	private final MypageService mypageService;


	//마이페이지
	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		
		mypageService.mypagelist(userDetails,model);
		
		return "/views/personnel/employee/mypage";
	}
	
	
	//프로필업데이트
	@PutMapping("/mypage/profile-update")
	public String postMethodName(@AuthenticationPrincipal CustomUserDetails userDetails,ProfileUpdateDTO dto) {
		
		mypageService.profileUpdate(userDetails,dto);
		
		
		return "redirect:/mypage";
	}
	
	
	//현재 비밀번호 체크
	@ResponseBody
	@PostMapping("/personnel/check-password")
	public  Map<String, Boolean>  postMethodName(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(name = "pass") String pass) {
		
		 Long userId = userDetails.getId();
		 
		 boolean isValid = mypageService.checkPassword(userId, pass);
		   
		
		  return Collections.singletonMap("valid", isValid);
	}
	
	
	//비밀번호 업데이트
	
	@PutMapping("/personnel/new-pass")
	public String putMethodName(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(name = "newpass") String newpass) {
	
		mypageService.passUpdate(userDetails,newpass);
		
		return "redirect:/mypage";
	}
	
	
	
	
	
	
	
	
	
}
