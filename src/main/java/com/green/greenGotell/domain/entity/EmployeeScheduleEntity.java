package com.green.greenGotell.domain.entity;

import java.time.LocalTime;

import org.hibernate.annotations.DynamicUpdate;

import com.green.greenGotell.domain.dto.EmployeeScheduleListDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "employeeSchedule")
@Entity
public class EmployeeScheduleEntity {
	
	//no
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	
	//직원코드
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", unique = true)
	private EmployeesEntity employee;
    
    //출근스케쥴
    @Column(nullable = false)
    private LocalTime scheduledStart;
    
    
    //퇴근스케줄
    @Column(nullable = false)
    private LocalTime scheduledEnd;


	public EmployeeScheduleListDTO toEmployeeScheduleListDTO(){
		
		
		return EmployeeScheduleListDTO.builder().employee(employee.toEmployeeDTO()).scheduledStart(scheduledStart).scheduledEnd(scheduledEnd).build();
		
	};

	

}