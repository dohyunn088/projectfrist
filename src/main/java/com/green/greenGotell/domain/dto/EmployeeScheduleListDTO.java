package com.green.greenGotell.domain.dto;

import java.time.LocalTime;

import com.green.greenGotell.domain.entity.EmployeesEntity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmployeeScheduleListDTO {
	
	 private EmployeeListDTO employee;
	
	 private LocalTime scheduledStart;
	 
	  private LocalTime scheduledEnd;

}
