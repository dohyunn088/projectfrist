package com.green.greenGotell.controller;// CalendarController.java
import com.green.greenGotell.domain.dto.EventDTO;
import com.green.greenGotell.domain.entity.Event;
import com.green.greenGotell.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final EventService eventService;

    @GetMapping
    public String showCalendar(Model model) throws Exception {
        List<EventDTO> events = eventService.getAllEvents(); // 모든 이벤트를 가져오는 서비스 메서드
        ObjectMapper mapper = new ObjectMapper();
        String eventStr = mapper.writeValueAsString(events);
        System.out.println(">>>" + eventStr);
        model.addAttribute("eventStr", eventStr); // Thymeleaf에 이벤트 데이터 전달
        model.addAttribute("events", events);
        return "views/calendar/calendar"; // Thymeleaf 템플릿 파일명 (calendar.html)
    }

    @GetMapping("/events")
    @ResponseBody
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addEvent(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) {
        Event event = Event.builder()
                .start(start).title(title).description(description)
                .build();

        eventService.saveEvent(event);

        Map<String, String> response = new HashMap<>();
        response.put("message", "일정이 성공적으로 추가되었습니다.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateEvent(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) {
        eventService.updateEvent(id, title, description);

        Map<String, String> response = new HashMap<>();
        response.put("message", "일정이 성공적으로 수정되었습니다.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteEvent(
            @RequestBody Map<String, Object> payload
    ) {
        Long id = Long.valueOf((String) payload.get("id"));
        eventService.deleteEvent(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "일정이 성공적으로 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }
}
