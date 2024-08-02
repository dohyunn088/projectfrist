package com.green.greenGotell.domain.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.greenGotell.domain.entity.AttendanceEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;

public interface AttendanceEntityRepository extends JpaRepository<AttendanceEntity, Long> {

	AttendanceEntity findTopByEmployeeOrderByClokInDesc(EmployeesEntity employee);

	Optional<AttendanceEntity> findByEmployeeAndDate(EmployeesEntity employee, LocalDate now);

}
