package com.green.greenGotell.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.green.greenGotell.domain.dto.AttendanceListDTO;
import com.green.greenGotell.domain.enums.AttendanceStatus;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;
import com.green.greenGotell.domain.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "attendance")
@Entity
public class AttendanceEntity {
	
	//no
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	
	//직원코드
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private EmployeesEntity employee;
	
	
	//출근날짜 
    @Column(nullable = false)
    private LocalDate date;
	
    //출근시간
	@CreationTimestamp
	@Column(nullable = false,columnDefinition ="timestamp" )
	private LocalDateTime clokIn;
	
	
	 //퇴근시간
    @Setter
	@Column(nullable = false,columnDefinition ="timestamp" )
	private LocalDateTime clokOut;
	
	
	//일한시간(분)
    @Setter
	@Column(columnDefinition = "int")
	private int workHoursInMinutes; 
    
	//초과 근무시간(분)
    @Setter
	@Column(columnDefinition = "int")
	private int overtimeHoursInMinutes;
	
	//이슈
	@Column(columnDefinition = "varchar(20)")
	private String commutingIssues;
	
	
	// 근무상태
	@Setter
	@Enumerated(EnumType.STRING)
	private AttendanceStatus attendanceStatus;
	
	
	//자동으로 출근시간을 찍으면 이 출근시간의 날짜가 출근날짜에 저장 됌 
    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = this.clokIn.toLocalDate();
        }
    }
    
    
    // toAttendanceListDTO
    
    public AttendanceListDTO toAttendanceListDTO() {
    	
    	
		return AttendanceListDTO.builder().name(employee.getName()).date(date).clokIn(clokIn.toString())
				                          .clokOut(clokOut !=null? clokOut.toString() : null)
				                          .workHoursInMinutes(workHoursInMinutes).overtimeHoursInMinutes(overtimeHoursInMinutes/60).attendanceStatus(attendanceStatus).commutingIssues(commutingIssues).department(employee.getDepartment()).roles(employee.getRoles()) .build();
    	
    };
    

	

}