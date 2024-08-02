package com.green.greenGotell.domain.repository;

import com.green.greenGotell.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // 기본적인 CRUD 메서드는 JpaRepository에서 제공하므로 추가적인 메서드가 필요하면 여기에 작성
}