package com.green.greenGotell.domain.entity;

import com.green.greenGotell.domain.dto.EventDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String start;

    @Column(nullable = false)
    private String title;

    private String description;

    public EventDTO toEventDTO(){
        return EventDTO.builder()
                .id(id)
                .start(start)
                .title(title)
                .build();
    }
}