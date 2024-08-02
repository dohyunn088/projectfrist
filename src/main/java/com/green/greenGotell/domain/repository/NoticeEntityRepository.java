package com.green.greenGotell.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.greenGotell.domain.entity.NoticeEntity;

public interface NoticeEntityRepository extends JpaRepository<NoticeEntity, Long>{

	//엔티티 기준 문법에 맞지 않으면
	Page<NoticeEntity> findAllByDivision(String division, Pageable pageable);

	Page<NoticeEntity> findAllByEmployeeId(Long userId, Pageable pageable);
	
	@Query("SELECT n FROM NoticeEntity n WHERE " +
	        "(n.division = :division OR :division = '1') AND " +
	        "(CASE WHEN :searchType = 'title' THEN n.title LIKE %:keyword% " +
	        "WHEN :searchType = 'content' THEN n.content LIKE %:keyword% " +
	        "WHEN :searchType = 'both' THEN n.title LIKE %:keyword% OR n.content LIKE %:keyword% " +
	        "END)")
	Page<NoticeEntity> searchNotices(
	        @Param("division") String division,
	        @Param("searchType") String searchType,
	        @Param("keyword") String keyword, Pageable pageable
	);
}
