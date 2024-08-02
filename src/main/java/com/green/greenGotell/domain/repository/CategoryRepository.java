package com.green.greenGotell.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.domain.entity.CategoryEntity;
import com.green.greenGotell.domain.entity.ItemEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
	List<CategoryEntity> findByParentIsNull();
	
	@Query("SELECT c FROM CategoryEntity c WHERE c.parent.id = :parentId AND c.level = :level")
	List<CategoryEntity> findByParentIdAndLevel(@Param("parentId") Long parentId, @Param("level") int level);

	List<CategoryEntity> findByParent_id(Long parentId);

	void save(ItemEntity entity);

	List<CategoryEntity> findAllByParentIsNull();
	
	
}
