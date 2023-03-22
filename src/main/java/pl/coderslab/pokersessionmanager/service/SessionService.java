package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
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
        if (session == null) {
            throw new RuntimeException("Creating session failed. Session is null.");
        }
        addOwner(session);
        addSessionDetails(session);
        sessionRepository.save(session);
    }

    public void edit(Session session) {
        if (session == null) {
            throw new RuntimeException("Editing session failed. Session id is null.");
        }
        sessionRepository.save(session);
    }

    public List<Session> findSessionsByPlayerId(Long playerId) {
        if (playerId == null) {
            throw new RuntimeException("Searching all player sessions failed. Player id is null");
        }
        return sessionRepository.findSessionsByPlayerId(playerId);
    }

    public Session findById(Long sessionId) {
        if (sessionId == null) {
            throw new RuntimeException("Searching for session failed. Session id is null.");
        }
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Searching for session failed. Unrecognized session id: " + sessionId));

        loadTournamentsToSession(session);

        User user = userService.getLoggedUser();
        if (!checkIfSessionBelongsToUser(session, user)) {
            throw new RuntimeException("Searching for session failed. No permission to processing with this session.");
        }

        return session;
    }

    public void delete(Long sessionId) {
        if (sessionId == null) {
            throw new RuntimeException("Deleting session failed. Session id is null.");
        }
        sessionRepository.delete(findById(sessionId));
    }

    public void loadTournamentsToSession(Session session) {
        if (session == null) {
            throw new RuntimeException("Loading tournaments to session failed. Session is null.");
        }
        Hibernate.initialize(session.getSessionTournaments());
    }

    public boolean checkIfSessionBelongsToUser(Session session, User user) {
        if (session == null) {
            throw new RuntimeException("Checking if session belongs to user failed. Session is null.");
        }
        if (user == null) {
            throw new RuntimeException("Checking if session belongs to user failed. User is null.");
        }
        if (userService.isLoggedAsAdmin()) {
            return true;
        }
        Optional<User> owner = Optional.ofNullable(session.getPlayer());
        if (owner.isEmpty()) {
            throw new RuntimeException("Checking if session belongs to user failed. Session hasn't owner.");
        }
        return session.getPlayer().equals(user);
    }

    private void addOwner(Session session) {
        if (session == null) {
            throw new RuntimeException("Setting session owner failed. Session is null");
        }

        if (userService.isLoggedAsUser()) {
            session.setPlayer((Player) userService.getLoggedUser());
        } else

            throw new RuntimeException("Setting owner for session failed. Owner must have Player class.");
    }

    private void addSessionDetails(Session session) {
        if (session == null) {
            throw new RuntimeException("Setting session details failed. Session is null");
        }
        session.setTournamentCount(session.getSessionTournaments().size());
        session.setTotalCost(countTotalSessionCost(session));
    }

    private double countTotalSessionCost(Session session) {
        if (session == null) {
            throw new RuntimeException("Counting total session cost failed. Session is null.");
        }
        return session.getSessionTournaments()
                .stream()
                .mapToDouble(AbstractTournament::getBuyIn)
                .sum();
    }


}
