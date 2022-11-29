package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.repository.SessionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void create(Session session) {
        sessionRepository.save(session);
    }

    public List<Session> findAllUserSessions(Long userId) {
        return sessionRepository.findAllUserSessions(userId);
    }

    public Session findById(Long sessionId) {
        Session session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert session by session Id."));

        loadTournamentsToSession(session);
        return session;

    }

//    public Optional<Session> findBySessionName(User user, String sessionName) {
//        return sessionRepository.findFirstByUserIdAndName(user.getId(), sessionName);
//    }

//    public List<Session> findAllUserSessions(Long userId) {
//        return sessionRepository.findAllByUserId(userId);
//    }

    public void delete(Long sessionId) {
        Session session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert session by session Id."));
        sessionRepository.delete(session);
    }

    public void loadTournamentsToSession(Session session) {
        Hibernate.initialize(session.getSessionTournaments());
    }


}
