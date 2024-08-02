package com.green.greenGotell.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileUpdateDTO {
	
	private String email;
	
	private String pass;
	
	private String phone;
	
	private String address;
	
	private MultipartFile fileContent;

}
