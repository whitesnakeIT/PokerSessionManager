package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.repository.PokerRoomRepository;
import pl.coderslab.pokersessionmanager.security.principal.CurrentUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class PokerRoomService {

    private final PokerRoomRepository pokerRoomRepository;

    public void create(PokerRoom pokerRoom) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ROLE_ANONYMOUS"))) {
            throw new RuntimeException("Anonymous User can't create Poker Rooms");
        }
        User user = ((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser();

        // admin edytuje turniej usera, turniej userowi sie usuwa
        if (user.hasRole("ROLE_ADMIN")) {
            pokerRoom.setScope(PokerRoomScope.GLOBAL.toString().toLowerCase());
        } else if (user.hasRole("ROLE_USER")) {
            pokerRoom.setScope(PokerRoomScope.LOCAL.toString().toLowerCase());
            pokerRoom.setUser(user);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PokerRoom> allPokerRooms = new ArrayList<>();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ROLE_ANONYMOUS"))) {
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
        Optional<User> owner = Optional.ofNullable(pokerRoom.getUser());
        if (owner.isEmpty()) {
            return false;
        }
        return pokerRoom.getUser().equals(user);
    }
}