package pl.coderslab.pokersessionmanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;
import pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room.PokerRoomSlim;
import pl.coderslab.pokersessionmanager.mapstruct.mappers.PokerRoomMapper;
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

    private final PokerRoomMapper pokerRoomMapper;

    public void create(PokerRoomSlim pokerRoomSlim) {
        if (pokerRoomSlim == null) {
            throw new RuntimeException("Creating poker room slim failed. Poker room slim is null.");
        }
        PokerRoom pokerRoom = pokerRoomMapper.pokerRoomSlimToPokerRoom(pokerRoomSlim);
        create(pokerRoom);
    }

    public void create(PokerRoom pokerRoom) {
        if (pokerRoom == null) {
            throw new RuntimeException("Creating poker room failed. Poker room is null.");
        }
        setPokerRoomDetails(pokerRoom);
        pokerRoomRepository.save(pokerRoom);
    }

    public void setPokerRoomDetails(PokerRoom pokerRoom) {
        if (pokerRoom == null) {
            throw new RuntimeException("Setting poker room details failed. Poker room is null.");
        }
        pokerRoom.setPokerRoomScope(getScopeByRole());
        setOwnerIfExist(pokerRoom);
    }


    private void setOwnerIfExist(PokerRoom pokerRoom) {
        if (pokerRoom == null) {
            throw new RuntimeException("Setting poker room owner failed. Poker room is null.");
        }
        if (userService.isLoggedAsUser()) {
            Player ower = (Player) userService.getLoggedUser();
            pokerRoom.setPlayer(ower);
        }
    }

    public PokerRoomScope getScopeByRole() {
        if (userService.isLoggedAsAdmin()) {
            return PokerRoomScope.GLOBAL;
        } else if (userService.isLoggedAsUser()) {
            return PokerRoomScope.LOCAL;
        }

        throw new RuntimeException("Setting poker room scope by role failed. Unrecognized logged user role.");
    }

    public void delete(Long pokerRoomId) {
        if (pokerRoomId == null) {
            throw new RuntimeException("Deleting poker room failed. Poker room id is null.");
        }
        pokerRoomRepository.delete(findById(pokerRoomId));
    }

    public PokerRoom findById(Long pokerRoomId) {
        if (pokerRoomId == null) {
            throw new RuntimeException("Searching for poker room failed. Poker room id is null.");
        }
        PokerRoom pokerRoom = pokerRoomRepository.findById(pokerRoomId).orElseThrow(() -> new RuntimeException("Searching for poker room failed. Unrecognized poker room id: " + pokerRoomId));

        if (!checkIfBelongsToUser(pokerRoom, userService.getLoggedUser())) {
            throw new RuntimeException("Searching for poker room failed. No permission to processing with this poker room.");
        }

        return pokerRoom;
    }

    public boolean checkIfBelongsToUser(PokerRoom pokerRoom, User user) {
        if (pokerRoom == null) {
            throw new RuntimeException("Checking if poker room belongs to user failed. Poker room is null.");
        }
        if (user == null) {
            throw new RuntimeException("Checking if poker room belongs to user failed. User is null.");
        }
        if (userService.isLoggedAsAdmin()) {
            return true;
        }

        if (!hasOwner(pokerRoom)) {
            return false;
        }

        return pokerRoom.getPlayer().equals(user);
    }

    public boolean hasOwner(PokerRoom pokerRoom) {
        if (pokerRoom == null) {
            throw new RuntimeException("Checking if poker room has owner. Poker room is null.");
        }
        Optional<User> owner = Optional.ofNullable(pokerRoom.getPlayer());
        return owner.isPresent();
    }

    public void edit(PokerRoomSlim pokerRoomSlim) {
        if (pokerRoomSlim == null) {
            throw new RuntimeException("Editing poker room failed. Poker room slim id is null.");
        }
        PokerRoom pokerRoom = pokerRoomMapper.updatePokerRoomFromPokerRoomSlim(pokerRoomSlim, findById(pokerRoomSlim.getId()));

        pokerRoomRepository.save(pokerRoom);
    }

    public List<PokerRoomSlim> findGlobalPokerRoomsSlim() {
        return findGlobalPokerRooms().stream().map(pokerRoomMapper::pokerRoomToPokerRoomSlim).toList();
    }

    public List<PokerRoom> findGlobalPokerRooms() {
        return pokerRoomRepository.findGlobalPokerRooms();
    }

    public List<PokerRoomSlim> findAvailablePokerRoomsSlimForPlayer() {
        return findAvailablePokerRoomsForPlayer().stream().map(pokerRoomMapper::pokerRoomToPokerRoomSlim).toList();
    }

    public List<PokerRoom> findAvailablePokerRoomsForPlayer() {
        List<PokerRoom> allPokerRooms = new ArrayList<>();
        allPokerRooms.addAll(findPokerRoomsByPlayerId(userService.getLoggedUserId()));
        allPokerRooms.addAll(findGlobalPokerRooms());

        return allPokerRooms;
    }

    public List<PokerRoom> findPokerRoomsByPlayerId(Long userId) {
        if (userId == null) {
            throw new RuntimeException("Searching for user poker rooms failed. User id is null.");
        }
        return pokerRoomRepository.findPokerRoomsByPlayerId(userId);
    }

    public List<PokerRoomSlim> findAllSlimByScope(PokerRoomScope scope) {
        if (scope == null) {
            throw new RuntimeException("Searching for poker rooms slim failed. Poker room slim scope is null.");
        }
        return findAllByScope(scope).stream().map(pokerRoomMapper::pokerRoomToPokerRoomSlim).toList();
    }

    public List<PokerRoom> findAllByScope(PokerRoomScope scope) {
        if (scope == null) {
            throw new RuntimeException("Searching for poker rooms failed. Poker room scope is null.");
        }
        switch (scope) {
            case LOCAL -> {
                return findPokerRoomsByPlayerId(userService.getLoggedUserId());
            }

            case GLOBAL -> {
                return findGlobalPokerRooms();
            }
        }
        throw new RuntimeException("Searching for poker rooms failed. Unrecognized poker room scope: " + scope);
    }

    public PokerRoomSlim findSlimById(Long pokerRoomSlimId) {
        if (pokerRoomSlimId == null) {
            throw new RuntimeException("Searching for poker room slim failed. Poker room slim id is null.");

        }
        return pokerRoomMapper.pokerRoomToPokerRoomSlim(findById(pokerRoomSlimId));
    }


}