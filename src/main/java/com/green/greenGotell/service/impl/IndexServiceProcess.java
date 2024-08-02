package com.green.greenGotell.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.green.greenGotell.domain.dto.AttendanceListDTO;
import com.green.greenGotell.domain.entity.AttendanceEntity;
import com.green.greenGotell.domain.entity.EmployeeScheduleEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.enums.AttendanceStatus;
import com.green.greenGotell.domain.repository.AttendanceEntityRepository;
import com.green.greenGotell.domain.repository.EmployeePhotoEntityRepository;
import com.green.greenGotell.domain.repository.EmployeeScheduleEntityRepository;
import com.green.greenGotell.domain.repository.EmployeesEntityRepository;
import com.green.greenGotell.security.CustomUserDetails;
import com.green.greenGotell.service.IndexService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IndexServiceProcess implements IndexService {
	
	private final EmployeesEntityRepository employeesEntityRepository;
	
	private final EmployeePhotoEntityRepository employeePhotoEntityRepository;
	
	private final AttendanceEntityRepository attendanceEntityRepository;
	
	private final EmployeeScheduleEntityRepository employeeScheduleEntityRepository;
	
	
	

   //프로필,출퇴근 스케줄 조회
	@Override
	public void list(CustomUserDetails userDetails, org.springframework.ui.Model model) {
	
		EmployeesEntity employee = employeesEntityRepository.findById(userDetails.getId()).orElseThrow();
		
		model.addAttribute("ProfilePhoto",employeePhotoEntityRepository.findByEmployeeId(userDetails.getId()).orElseThrow().toProfileImageDTO() );
		model.addAttribute("todaySchedule", employeeScheduleEntityRepository.findByEmployee(employee).orElseThrow().toEmployeeScheduleListDTO());
	
		
	}




    //출근시간 저장
	@Transactional
	@Override
	public void clockIn(CustomUserDetails userDetails) {
		

		EmployeesEntity employee = employeesEntityRepository.findById(userDetails.getId()).orElseThrow();
		
		EmployeeScheduleEntity schedule = employeeScheduleEntityRepository.findByEmployee(employee).orElseThrow();
		
		LocalDateTime now = LocalDateTime.now();
		
		//마지막 출근 후 16시간이 지났는지 확인
        AttendanceEntity lastAttendance = attendanceEntityRepository.findTopByEmployeeOrderByClokInDesc(employee);
        if (lastAttendance != null && Duration.between(lastAttendance.getClokIn(), now).toHours() < 16) {
            throw new IllegalStateException("Cannot clock in within 16 hours of last clock-in");
        }
        
        //출근정보와 스캐쥴 비교해 상태 저장
        
        AttendanceEntity attendance = AttendanceEntity.builder().employee(employee).date(now.toLocalDate()).clokIn(now).build();
        
        if (now.toLocalTime().isAfter(schedule.getScheduledStart())) {
            attendance.setAttendanceStatus(AttendanceStatus.LATE);
        } else {
            attendance.setAttendanceStatus(AttendanceStatus.NORMAL);
        }
        
        attendanceEntityRepository.save(attendance);
        

	}


    //퇴근시간 저장
	@Transactional
	@Override
	public void clockOut(CustomUserDetails userDetails) {

		EmployeesEntity employee = employeesEntityRepository.findById(userDetails.getId()).orElseThrow();
		
		EmployeeScheduleEntity schedule = employeeScheduleEntityRepository.findByEmployee(employee).orElseThrow();
		
		LocalDateTime now = LocalDateTime.now();
		
		AttendanceEntity attendance = attendanceEntityRepository.findByEmployeeAndDate(employee, LocalDate.now()).orElseThrow();
		
		attendance.setClokOut(now);
		
		 // Calculate work hours
        Duration workDuration = Duration.between(attendance.getClokIn(), now);
        attendance.setWorkHoursInMinutes((int) workDuration.toMinutes());

        // Calculate overtime
        Duration scheduledDuration = Duration.between(schedule.getScheduledStart(), schedule.getScheduledEnd());
        if (workDuration.compareTo(scheduledDuration) > 0) {
            attendance.setOvertimeHoursInMinutes((int) (workDuration.toMinutes() - scheduledDuration.toMinutes()));
        }

        // Update attendance status
        if (now.toLocalTime().isBefore(schedule.getScheduledEnd())) {
            if (attendance.getAttendanceStatus() == AttendanceStatus.LATE) {
                attendance.setAttendanceStatus(AttendanceStatus.LATE_AND_EARLY_LEAVE);
            } else {
                attendance.setAttendanceStatus(AttendanceStatus.EARLY_DEPARTURE);
            }
        }

        attendance = attendanceEntityRepository.save(attendance);
     
    }
		
		
		




    //실시간 출퇴근상태 보여주기
	@Override
	public AttendanceListDTO AttendanceStatus(CustomUserDetails userDetails) {
	    EmployeesEntity employee = employeesEntityRepository.findById(userDetails.getId()).orElseThrow();
	    
	    return attendanceEntityRepository.findByEmployeeAndDate(employee, LocalDate.now())
	        .map(this::convertToAttendanceListDTO)
	        .orElseGet(() -> AttendanceListDTO.builder()
	            .employee(employee.getId())
	            .date(LocalDate.now())
	            .build());
	}

	private AttendanceListDTO convertToAttendanceListDTO(AttendanceEntity attendance) {
	    return AttendanceListDTO.builder()
	        .employee(attendance.getEmployee().getId())
	        .date(attendance.getDate())
	        .clokIn(attendance.getClokIn() != null ? attendance.getClokIn().toString() : null)
	        .clokOut(attendance.getClokOut() != null ? attendance.getClokOut().toString() : null)
	        .overtimeHoursInMinutes(attendance.getOvertimeHoursInMinutes())
	        .attendanceStatus(attendance.getAttendanceStatus())
	        .workHoursInMinutes(attendance.getWorkHoursInMinutes())
	        .build();
	}
	
	
	//

}
