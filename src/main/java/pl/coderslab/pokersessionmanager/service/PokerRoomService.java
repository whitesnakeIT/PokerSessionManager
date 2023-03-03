package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.repository.PokerRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class PokerRoomService {

    private final PokerRoomRepository pokerRoomRepository;

    private final UserService userService;

    public void create(PokerRoom pokerRoom) {
        pokerRoomRepository.save(setOwnerAndDetails(pokerRoom));
    }

    public PokerRoom setOwnerAndDetails(PokerRoom pokerRoom) {

        // If we are creating new Poker Room, before save() Object's 'id' is not known
        if (pokerRoom.getId() == null) {
            User loggedUser = userService.getLoggedUser();

            if (loggedUser.hasRole(RoleName.ROLE_ADMIN)) {
                pokerRoom.setPokerRoomScope(PokerRoomScope.GLOBAL);
            } else if (loggedUser.hasRole(RoleName.ROLE_USER)) {
                pokerRoom.setPokerRoomScope(PokerRoomScope.LOCAL);
                pokerRoom.setPlayer((Player) loggedUser);
            }
        }
        // If we are editing existed PokerRoom
        else {
            PokerRoom pokerRoomFromDb = findById(pokerRoom.getId());
            if (pokerRoomFromDb.getPlayer() != null) {
                pokerRoom.setPlayer(pokerRoomFromDb.getPlayer());
            }
            pokerRoom.setPokerRoomScope(pokerRoomFromDb.getPokerRoomScope());
        }
        return pokerRoom;
    }

//    public void edit(PokerRoom pokerRoom) {
//        PokerRoom pokerRoomFromDb = findById(pokerRoom.getId());
//        if (pokerRoomFromDb.getPlayer() != null) {
//            pokerRoom.setPlayer(pokerRoomFromDb.getPlayer());
//        }
//        pokerRoom.setScope(pokerRoomFromDb.getScope());
//
//        pokerRoomRepository.save(pokerRoom);
//    }


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

        User loggedUser = userService.getLoggedUser();

        if (!checkIfPokerRoomBelongsToUser(pokerRoom, loggedUser)) {
            throw new RuntimeException("Poker Room not belongs to You");
        }

        return pokerRoom;
    }

    public List<PokerRoom> findGlobalPokerRooms() {
        return pokerRoomRepository.findGlobalPokerRooms();
    }

    public List<PokerRoom> findAvailablePokerRoomsForPlayer() {
        List<PokerRoom> allPokerRooms = new ArrayList<>();
        allPokerRooms.addAll(findPokerRoomsByUserId(userService.getLoggedUser().getId()));
        allPokerRooms.addAll(findGlobalPokerRooms());

        return allPokerRooms;
    }

    public boolean checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user) {
        if (user.hasRole(RoleName.ROLE_ADMIN)) {
            return true;  // admin może usuwać edytować wszystko
        }
        Optional<User> owner = Optional.ofNullable(pokerRoom.getPlayer());
        if (owner.isEmpty()) {
            return false;
        }
        return pokerRoom.getPlayer().equals(user);
    }

    public List<PokerRoom> findAllByScope(PokerRoomScope scope) {
        switch (scope){
            case LOCAL -> {
                return findPokerRoomsByUserId(userService.getLoggedUser().getId());
            }

            case GLOBAL -> {
               return findGlobalPokerRooms();
            }
        }
        throw new RuntimeException("I dont know PokerRoomScope: " + scope);

    }
}