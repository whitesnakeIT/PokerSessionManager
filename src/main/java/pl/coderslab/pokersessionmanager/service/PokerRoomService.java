package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.PokerRoomRepository;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

//import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class PokerRoomService {

    private final PokerRoomRepository pokerRoomRepository;

    private final UtilityService utilityService;

    public void create(PokerRoom pokerRoom) {
        if (utilityService.checkIfAnonymous()) {
            throw new RuntimeException("Anonymous User can't create Poker Rooms");
        }
        User user = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();

        // admin edytuje turniej usera, turniej userowi sie usuwa
        if (user.hasRole(RoleName.ROLE_ADMIN.name())) {
            pokerRoom.setScope(PokerRoomScope.GLOBAL.name().toLowerCase());
        } else if (user.hasRole(RoleName.ROLE_USER.name())) {
            pokerRoom.setScope(PokerRoomScope.LOCAL.name().toLowerCase());
            pokerRoom.setPlayer((Player) user);
        }
        pokerRoomRepository.save(pokerRoom);
    }

    public List<PokerRoom> findAll() {
        return pokerRoomRepository.findAll();
    }

    public List<PokerRoom> findPokerRoomsByUserId(Long userId) {
        return pokerRoomRepository.findPokerRoomsByUserId(userId);
    }

    public void delete(Long pokerRoomId) {
        PokerRoom pokerRoom = findById(pokerRoomId);
        pokerRoomRepository.delete(pokerRoom);
    }

    public PokerRoom findById(Long pokerRoomId) {
        PokerRoom pokerRoom = pokerRoomRepository.findById(pokerRoomId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert pokerRoom by pokerRoom Id."));

        User user = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();

        if (!checkIfPokerRoomBelongsToUser(pokerRoom, user)) {
            throw new RuntimeException("Poker Room not belongs to You");
        }

        return pokerRoom;
    }

    public List<PokerRoom> findAllGlobal() {
        return pokerRoomRepository.findAllGlobal();
    }

    public List<PokerRoom> findAllByRole() {
        List<PokerRoom> allPokerRooms = new ArrayList<>();
        if (utilityService.checkIfAnonymous()) {
            allPokerRooms.addAll(findAllGlobal());
        } else {
            User user = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            if (user.hasRole("ROLE_ADMIN")) {
                allPokerRooms.addAll(findAll());
            } else if (user.hasRole("ROLE_USER")) {
                allPokerRooms.addAll(findPokerRoomsByUserId(user.getId()));
                allPokerRooms.addAll(findAllGlobal());
            }
        }

        return allPokerRooms;
    }

    public boolean checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user) {
        if (user.hasRole("ROLE_ADMIN")) {
            return true;  // admin może usuwać edytować wszystko
        }
        Optional<User> owner = Optional.ofNullable(pokerRoom.getPlayer());
        if (owner.isEmpty()) {
            return false;
        }
        return pokerRoom.getPlayer().equals(user);
    }
}