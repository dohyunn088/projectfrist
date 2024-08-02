package com.green.greenGotell.service;

import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.CreateEmployeeDTO;
import com.green.greenGotell.domain.dto.ProfileUpdateDTO;
import com.green.greenGotell.security.CustomUserDetails;

public interface MypageService{

	void mypagelist(CustomUserDetails userDetails, Model model);

	void profileUpdate(CustomUserDetails userDetails, ProfileUpdateDTO dto);

	boolean checkPassword(Long userId, String pass);

	void passUpdate(CustomUserDetails userDetails, String newpass);

}
