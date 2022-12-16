package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.repository.SessionRepository;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

//    private final UtilityService utilityService;

    public void create(Session session) {
        Player player = (Player) ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();
        session.setPlayer(player);
        session.setTournamentCount(session.getSessionTournaments().size());
        session.setTotalCost(session.getSessionTournaments().stream().mapToDouble(AbstractTournament::getBuyIn).sum());
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

        User user = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();
        if (!checkIfPokerSessionBelongsToUser(session, user)) {
            throw new RuntimeException("Session not belongs to You");
        }
        return session;

    }

//    public Optional<Session> findBySessionName(User user, String sessionName) {
//        return sessionRepository.findFirstByUserIdAndName(user.getId(), sessionName);
//    }

    public void delete(Long sessionId) {
        Session session = findById(sessionId);
        sessionRepository.delete(session);
    }

    public void loadTournamentsToSession(Session session) {
        Hibernate.initialize(session.getSessionTournaments());
    }

    public boolean checkIfPokerSessionBelongsToUser(Session session, User user) {
        if (user.hasRole("ROLE_ADMIN")) {
            return false;  // admin nie ma sesji
        }
        Optional<User> owner = Optional.ofNullable(session.getPlayer());
        if (owner.isEmpty()) {
            return false;
        }
        return session.getPlayer().equals(user);
    }

}
