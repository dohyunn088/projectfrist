package com.green.greenGotell.service;


import com.green.greenGotell.domain.dto.EventDTO;
import com.green.greenGotell.domain.entity.Event;
import com.green.greenGotell.domain.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(Event::toEventDTO)
                .collect(Collectors.toList());
    }// 신규
    public void updateEvent(Long id, String title, String description) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(title);
        event.setDescription(description);
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}