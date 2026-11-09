package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session create(Session session) {
        return this.sessionRepository.save(session);
    }

    public void delete(Long id) {
        if (!this.sessionRepository.existsById(id)) {
            throw new EntityNotFoundException("This session doesn't exist");
        }

        this.sessionRepository.deleteById(id);
    }

    public List<Session> findAll() {
        return this.sessionRepository.findAll();
    }

    public Session getById(Long id) {
        return this.sessionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No session found " +
                "for the id"));
    }

    public Session update(Long id, Session session) {
        session.setId(id);
        return this.sessionRepository.save(session);
    }

    public void participate(Long id, User user) {
        Session session = this.sessionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No Session" +
                        " found for this id"));

        boolean alreadyParticipate = session.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()));
        if (alreadyParticipate) {
            throw new BadRequestException("This user is already register for this session");
        }

        session.getUsers().add(user);

        this.sessionRepository.save(session);
    }

    public void noLongerParticipate(Long id, User user) {
        Session session = this.sessionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No Session" +
                        " found for this id"));

        boolean alreadyParticipate = session.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()));
        if (!alreadyParticipate) {
            throw new BadRequestException();
        }

        session.setUsers(
                session.getUsers().stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList()));

        this.sessionRepository.save(session);
    }
}
