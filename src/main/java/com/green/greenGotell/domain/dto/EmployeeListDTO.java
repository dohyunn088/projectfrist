package com.green.greenGotell.domain.dto;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;
import com.green.greenGotell.domain.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class EmployeeListDTO {
	
	private long id;
	
	private String name;
	
	private String email;
	
	private String pass;
	
	private String phone;
	
	private String address;
	
	private LocalDate hireDate;
	
	private Department department ;
	
	private EmployeeStatus employeeStatus ;
	
	private Set<Role> roles;
	
	//프로필사진
	 private byte[] fileContent;
	 private String profileImage;
	 
	 @Setter
	 private Role highestRole;
	
   //주소
	private String roadAddress;
	private String detailAddress;
	
	
	
	
	
	// 표시될 부서 
    public Role getHighRole() {
       
        return roles.stream()
                    .sorted((r1, r2) -> Integer.compare(r2.getNumber(), r1.getNumber())) // 우선순위 내림차순 정렬
                    .findFirst() // 가장 높은 우선순위의 역할을 반환
                    .orElse(null); // 역할이 없으면 null 반환
    }
    
   
    
    
    
  
 // 기본 주소와 상세 주소 나누기
    public EmployeeListDTO splitAddress() {
    	  if (address != null && !address.isEmpty()) {
              String[] parts = splitAddressLogic(address);
              this.roadAddress = parts[0];
              this.detailAddress = parts[1];
          }
		return this;
    }

    private String[] splitAddressLogic(String address) {
        // 숫자가 처음 등장하는 위치를 찾는 정규 표현식
        Pattern pattern = Pattern.compile("(\\d+[^\\d]*)$");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            int startIndex = matcher.start();
            return new String[]{
                address.substring(0, startIndex).trim(),
                address.substring(startIndex).trim()
            };
        }

        // 기본 주소와 상세 주소를 구분할 수 없는 경우
        return new String[]{address, ""};
    }
    
    
    

}
