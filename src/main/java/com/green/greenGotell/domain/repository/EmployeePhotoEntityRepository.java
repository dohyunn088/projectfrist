package com.green.greenGotell.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.greenGotell.domain.entity.EmployeePhotoEntity;
import com.green.greenGotell.domain.entity.EmployeesEntity;

public interface EmployeePhotoEntityRepository extends JpaRepository<EmployeePhotoEntity, Long> {

	  Optional<EmployeePhotoEntity> findByEmployeeId(Long employeeId);





}
