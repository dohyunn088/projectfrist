package com.green.greenGotell.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.greenGotell.domain.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>{

	Page<ItemEntity> findByCategory_id(Long maxCategoryId, Pageable pageable);
	
	@Query("SELECT i FROM ItemEntity i WHERE i.category.id = :categoryId " +
	           "OR i.category.id IN (SELECT c.id FROM CategoryEntity c WHERE c.parent.id = :categoryId) " +
	           "OR i.category.id IN (SELECT c2.id FROM CategoryEntity c1 JOIN c1.children c2 WHERE c1.parent.id = :categoryId)")
	Page<ItemEntity> findItemsByCategoryAndDescendants(@Param("categoryId") Long categoryId, Pageable pageable);
	
	
	@Query("SELECT i FROM ItemEntity i WHERE (i.category.id = :categoryId " +
	           "OR i.category.id IN (SELECT c.id FROM CategoryEntity c WHERE c.parent.id = :categoryId) " +
	           "OR i.category.id IN (SELECT c2.id FROM CategoryEntity c1 JOIN c1.children c2 WHERE c1.parent.id = :categoryId)) " +
	           "AND i.name LIKE %:searchTerm%")
	Page<ItemEntity> findItemsByCategoryAndDescendants(@Param("categoryId") Long categoryId, @Param("searchTerm") String searchTerm, Pageable pageable);
	
	//카테고리 대,중,소 분류대로 선택 조회 및 검색만 조회 및 대중소&&검색조회 등.. 가능 쿼리문
	@Query("SELECT i FROM ItemEntity i WHERE " +
	           "(CASE WHEN :categoryId IS NULL THEN i.name LIKE %:searchTerm% " +
	           "ELSE (i.category.id = :categoryId " +
	           "OR i.category.id IN (SELECT c.id FROM CategoryEntity c WHERE c.parent.id = :categoryId) " +
	           "OR i.category.id IN (SELECT c2.id FROM CategoryEntity c1 JOIN c1.children c2 WHERE c1.parent.id = :categoryId)) " +
	           "AND i.name LIKE %:searchTerm% END)")
    Page<ItemEntity> findItemsByCategoryAndDescendantsAndSearName(
            @Param("categoryId") Long categoryId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);

	//@Query("SELECT e FROM ItemEntity e WHERE (:name IS NULL OR e.name LIKE %:name%) AND (:category IS NULL OR e.category = :category)")
	//Page<ItemEntity> findBySearchItem(@Param("name") String name, @Param("category") Long category, Pageable pageable);

}
