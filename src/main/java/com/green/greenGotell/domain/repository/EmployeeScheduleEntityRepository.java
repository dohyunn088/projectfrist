package com.green.greenGotell.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.greenGotell.domain.entity.EmployeeScheduleEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;

public interface EmployeeScheduleEntityRepository extends JpaRepository<EmployeeScheduleEntity, Long>  {

	
	Optional<EmployeeScheduleEntity> findByEmployee(EmployeesEntity employee);

}
