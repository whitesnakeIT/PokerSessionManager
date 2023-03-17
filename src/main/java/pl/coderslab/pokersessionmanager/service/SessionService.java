package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final UserService userService;

    public void create(Session session) {
        sessionRepository.save(setOwnerAndDetails(session));
    }

    public Session setOwnerAndDetails(Session session) {
        if (session.getId() == null) {
            User loggedUser = userService.getLoggedUser();
            session.setPlayer((Player) loggedUser);
        } else {
            Session sessionFromDb = findById(session.getId());
            session.setPlayer(sessionFromDb.getPlayer());
        }
        session.setTournamentCount(session.getSessionTournaments().size());
        session.setTotalCost(countTotalSessionCost(session));
        return session;
    }

    public List<Session> findAllUserSessions(Long userId) {
        return sessionRepository.findSessionsByPlayerId(userId);
    }

    public Session findById(Long sessionId) {
        Session session = sessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert session by session Id."));

        loadTournamentsToSession(session);

        User user = userService.getLoggedUser();
        if (!checkIfPokerSessionBelongsToUser(session, user)) {
            throw new RuntimeException("Session not belongs to You");
        }
        return session;

    }

    public void delete(Long sessionId) {
        sessionRepository.delete(findById(sessionId));
    }

    public void loadTournamentsToSession(Session session) {
        Hibernate.initialize(session.getSessionTournaments());
    }

    public boolean checkIfPokerSessionBelongsToUser(Session session, User user) {
        // Administrator can't have poker sessions
        if (user.hasRole(RoleName.ROLE_ADMIN)) {
            return true;
        }
        Optional<User> owner = Optional.ofNullable(session.getPlayer());
        if (owner.isEmpty()) {
            return false;
        }
        return session.getPlayer().equals(user);
    }

    public double countTotalSessionCost(Session session) {
        return session.getSessionTournaments().stream().mapToDouble(AbstractTournament::getBuyIn).sum();
    }
}
