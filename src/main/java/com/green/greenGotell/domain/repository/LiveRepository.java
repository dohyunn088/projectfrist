package com.green.greenGotell.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.greenGotell.domain.entity.ItemEntity;

public interface LiveRepository extends JpaRepository<ItemEntity, Long>{

	Page<ItemEntity> findAll(Pageable pageable);
	@Query("SELECT e FROM ItemEntity e WHERE (:name IS NULL OR e.name LIKE %:name%) AND (:category IS NULL OR e.category = :category)")
	Page<ItemEntity> findBySearchItem(@Param("name") String name, @Param("category") String category, Pageable pageable);


}
