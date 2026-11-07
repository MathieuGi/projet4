package com.openclassrooms.starterjwt.controllers;


import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.services.SessionService;
import com.openclassrooms.starterjwt.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
@Log4j2
@RequiredArgsConstructor
public class SessionController {
    private final SessionMapper sessionMapper;
    private final SessionService sessionService;
    private final UserService userService;

    @GetMapping("/{id}")
    @NumberFormat
    public ResponseEntity<SessionDto> findById(@PathVariable("id") String id) {
        try {
            Session session = this.sessionService.getById(Long.valueOf(id));
            return ResponseEntity.ok(this.sessionMapper.toDto(session));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Session id should be a number");
        }
    }

    @GetMapping()
    public ResponseEntity<List<SessionDto>> findAll() {
        List<Session> sessions = this.sessionService.findAll();

        return ResponseEntity.ok(this.sessionMapper.toDto(sessions));
    }

    @PostMapping()
    public ResponseEntity<SessionDto> create(@Valid @RequestBody SessionDto sessionDto) {
        Session session = this.sessionService.create(this.sessionMapper.toEntity(sessionDto));

        return ResponseEntity.ok().body(this.sessionMapper.toDto(session));
    }

    @PutMapping("{id}")
    public ResponseEntity<SessionDto> update(@PathVariable("id") String id, @Valid @RequestBody SessionDto sessionDto) {
        Session session;

        try {
            session = this.sessionService.update(Long.parseLong(id), this.sessionMapper.toEntity(sessionDto));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Session id should be a number");
        }

        return ResponseEntity.ok(this.sessionMapper.toDto(session));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") String id) {
        try {
            long idValue = Long.parseLong(id);
            this.sessionService.delete(idValue);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Session id should be a number");
        }

        return ResponseEntity.ok(new MessageResponse("Session successfully deleted"));

    }

    @PostMapping("{id}/participate/me")
    public ResponseEntity<MessageResponse> participate(@PathVariable("id") String id) {
        try {
            long sessionId = Long.parseLong(id);
            this.sessionService.participate(sessionId, userService.me());

            return ResponseEntity.ok(new MessageResponse("You have been registered for this session"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Session id should be a number");
        }
    }

    @DeleteMapping("{id}/participate/me")
    public ResponseEntity<MessageResponse> noLongerParticipate(@PathVariable("id") String id) {
        try {
            this.sessionService.noLongerParticipate(Long.parseLong(id), userService.me());

            return ResponseEntity.ok(new MessageResponse("You have been unregistered for this session"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Session id should be a number");
        }
    }
}
