package com.green.greenGotell.domain.dto;



import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileImageDTO {

	// 프로필사진
	private byte[] fileContent;
	private String profileImage;

}


