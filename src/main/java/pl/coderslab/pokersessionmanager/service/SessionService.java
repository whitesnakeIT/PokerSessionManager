package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.SessionRepository;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final UserService userService;

    public void create(Session session) {
        if (session.getPlayer()==null) {
            Player player = (Player) userService.getLoggedUser();
            session.setPlayer(player);
        }
        session.setTournamentCount(session.getSessionTournaments().size());
        session.setTotalCost(countTotalSessionCost(session));
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
