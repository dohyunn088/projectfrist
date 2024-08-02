package com.green.greenGotell.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.greenGotell.domain.entity.EmployeesEntity;
import com.green.greenGotell.domain.enums.Department;
import com.green.greenGotell.domain.enums.EmployeeStatus;

public interface EmployeesEntityRepository extends JpaRepository<EmployeesEntity, Long> {
	
	Optional<EmployeesEntity> findByEmail(String email);


	
	   @Query("SELECT e FROM EmployeesEntity e WHERE " +
	           "(:department IS NULL OR e.department = :department) AND " +
	           "(:employeeStatus IS NULL OR e.employeeStatus = :employeeStatus) AND " +
	           "(:name IS NULL OR e.name LIKE %:name%)")
	    Page<EmployeesEntity> findBySearchEmployee(
	            @Param("department") Department department,
	            @Param("employeeStatus") EmployeeStatus employeeStatus,
	            @Param("name") String name,
	            Pageable pageable);

}
