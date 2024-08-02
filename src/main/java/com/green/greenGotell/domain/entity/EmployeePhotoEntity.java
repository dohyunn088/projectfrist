package com.green.greenGotell.domain.entity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import com.green.greenGotell.domain.dto.EmployeeListDTO;
import com.green.greenGotell.domain.dto.ProfileImageDTO;
import com.green.greenGotell.domain.dto.ProfileUpdateDTO;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;
import com.green.greenGotell.domain.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
@Table(name = "employeePhoto")
@Entity
public class EmployeePhotoEntity {
	
    //프로필아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //프로필을 설정한 직원
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "id", unique = true, nullable = false)
    private EmployeesEntity employee;


    //프로필의 소스코드 
    @Lob
    @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] fileContent; //최대 16mb


    //프로필 사진을 바이트로 변환
    public EmployeePhotoEntity update(ProfileUpdateDTO dto) {
        // 기존의 파일 내용 저장
        byte[] existingFileContent = this.fileContent;

        try {
            if (dto.getFileContent() != null && !dto.getFileContent().isEmpty()) {
                // 파일 내용이 있을 경우 바이트 배열로 변환
                this.fileContent = dto.getFileContent().getBytes();
            } else {
                // 파일 내용이 없을 경우 기존 내용을 유지
                this.fileContent = existingFileContent;
            }
        } catch (IOException e) {
            // IOException 발생 시, 기존 내용을 유지
            this.fileContent = existingFileContent;
        }
        
        return this; // 업데이트 후 현재 객체를 반환
    }

    //직원 정보 얻어올때 dto
	public EmployeeListDTO toEmployeeDTO() {
		
		String base64Image = (fileContent != null) ? Base64.getEncoder().encodeToString(fileContent) : null;
		return EmployeeListDTO.builder().profileImage(base64Image).build();
		
	}

   //직원 이미지 파일 얻어올때 dto
	public ProfileImageDTO toProfileImageDTO() {
		
		String base64Image = (fileContent != null) ? Base64.getEncoder().encodeToString(fileContent) : null;
		return ProfileImageDTO.builder().profileImage(base64Image).build();
	
		
	}

 
	
	
	

}
