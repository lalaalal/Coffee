package com.lalaalal.coffee.controller.api;

import com.lalaalal.coffee.controller.SessionHelper;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventApiController extends SessionHelper {
    private final EventService eventService;

    @Autowired
    public EventApiController(EventService eventService, HttpSession httpSession) {
        super(httpSession);
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResultDTO> add(@RequestBody Event event) {
        Result result = eventService.addEvent(event);
        return createResultEntity(result);
    }

    @RequestMapping(value = "/{eventId}/cancel")
    public ResponseEntity<ResultDTO> cancel(@PathVariable("eventId") int eventId) {
        Result result = eventService.cancel(eventId);
        return createResultEntity(result);
    }
}
