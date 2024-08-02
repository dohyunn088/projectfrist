package com.green.greenGotell.domain.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.enums.AttendanceStatus;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.Role;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AttendanceListDTO {
	
	private Long employee;
	
	private String name;
	
	private Department department ;
	
	private LocalDate date;
	
	private String clokIn;
	
	private String clokOut;
	
	private int workHoursInMinutes; 
	
	private int overtimeHoursInMinutes;
	
	private AttendanceStatus attendanceStatus;
	
	private Set<Role> roles;
	
	
	private String commutingIssues;
	
	
	
	//총근무
    public String getFormattedWorkHours() {
          return formatMinutes(workHoursInMinutes);
    }
    
	//퇴근 버튼을 누르지 않았을때 보여줄 임시총근무
    public String getFormattedDummyWorkHours() {

    	LocalDateTime inTime = LocalDateTime.parse(clokIn);
        LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간 가져오기

        Duration duration = Duration.between(inTime, currentTime); // 현재 시간과 출근 시간 차이 계산
        long minutes = duration.toMinutes(); // 분 단위로 변환

        return formatMinutes((int) minutes);
    }
    
	//오버타임근무
    public String getFormattedOvertime() {
          return formatMinutes(overtimeHoursInMinutes);
    }


	
	//총근무, 오버타임근무 시간표시
	private String formatMinutes(int minutes) {
	    int hours = minutes / 60;
	    int remainingMinutes = minutes % 60;
	    int seconds = remainingMinutes % 60;
	    return String.format("%d:%02d:%02d", hours, remainingMinutes,seconds);
	}
	
	

    

}
