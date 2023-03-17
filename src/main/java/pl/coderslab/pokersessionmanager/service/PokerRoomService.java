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


    public void create(PokerRoom pokerRoom) {
        if (pokerRoom == null) {
            throw new RuntimeException("Creating poker room failed. Poker room is null.");
        }
//        PokerRoom pokerRoomEntity = setImmutableData(pokerRoom);
//        pokerRoomRepository.save(pokerRoomEntity);
        pokerRoomRepository.save(pokerRoom);
    }

    public void create(PokerRoomSlim pokerRoomSlim) {
        if (pokerRoomSlim == null) {
            throw new RuntimeException("Creating poker room slim failed. Poker room slim is null.");
        }
        PokerRoom pokerRoom = pokerRoomMapper.pokerRoomSlimToPokerRoom(pokerRoomSlim);
        create(pokerRoom);
    }


    private PokerRoom setImmutableData(PokerRoom pokerRoom) {
        pokerRoom.setPokerRoomScope(setPokerRoomScopeByRole());
        setOwnerIfExist(pokerRoom);
        return pokerRoom;
    }

    public PokerRoomScope setPokerRoomScopeByRole() {
        if (userService.isLoggedAsAdmin()) {
            return PokerRoomScope.GLOBAL;
        } else if (userService.isLoggedAsUser()) {
            return PokerRoomScope.LOCAL;
        }

        throw new RuntimeException("Setting poker room scope by role failed.");
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


    public List<PokerRoom> findPokerRoomsByPlayerId(Long userId) {
        if (userId == null) {
            throw new RuntimeException("Searching for user poker rooms failed. User id is null.");
        }
        return pokerRoomRepository.findPokerRoomsByPlayerId(userId);
    }


    public PokerRoom findById(Long pokerRoomId) {
        if (pokerRoomId == null) {
            throw new RuntimeException("Searching for poker room failed. Poker room id is null.");
        }
        PokerRoom pokerRoom = pokerRoomRepository.findById(pokerRoomId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert poker room by pokerRoom id: " + pokerRoomId));

        if (!checkIfPokerRoomBelongsToUser(pokerRoom, userService.getLoggedUser())) {
            throw new RuntimeException("Poker Room not belongs to You");
        }

        return pokerRoom;
    }

    public void delete(Long pokerRoomId) {
        if (pokerRoomId == null) {
            throw new RuntimeException("Deleting poker room failed. Poker room id is null.");
        }
        pokerRoomRepository.delete(findById(pokerRoomId));
    }

    public void edit(PokerRoomSlim pokerRoomSlim) {
        if (pokerRoomSlim == null) {
            throw new RuntimeException("Editing poker room failed. Poker room slim id is null.");
        }
        PokerRoom pokerRoom = pokerRoomMapper.
                updatePokerRoomFromPokerRoomSlim(
                        pokerRoomSlim,
                        findById(pokerRoomSlim.getId()));

        pokerRoomRepository.save(pokerRoom);
    }


    public boolean checkIfPokerRoomBelongsToUser(PokerRoom pokerRoom, User user) {
        if (pokerRoom == null) {
            throw new RuntimeException("Checking if poker room belongs to user failed. Poker room is null.");
        }
        if (user == null) {
            throw new RuntimeException("Checking if poker room belongs to user failed. User is null.");
        }
        if (userService.isLoggedAsAdmin()) {
            return true;
        }
        Optional<User> owner = Optional.ofNullable(pokerRoom.getPlayer());
        if (owner.isEmpty()) {
            return false;
        }
        return pokerRoom.getPlayer().equals(user);
    }

    public List<PokerRoom> findGlobalPokerRooms() {
        return pokerRoomRepository.findGlobalPokerRooms();
    }

    public List<PokerRoomSlim> findGlobalPokerRoomsSlim() {
        return findGlobalPokerRooms()
                .stream()
                .map(pokerRoomMapper::pokerRoomToPokerRoomSlim)
                .toList();
    }

    public List<PokerRoom> findAvailablePokerRoomsForPlayer() {
        List<PokerRoom> allPokerRooms = new ArrayList<>();
        allPokerRooms.addAll(findPokerRoomsByPlayerId(userService.getLoggedUser().getId()));
        allPokerRooms.addAll(findGlobalPokerRooms());

        return allPokerRooms;
    }

    public List<PokerRoomSlim> findAvailablePokerRoomsSlimForPlayer() {
        return findAvailablePokerRoomsForPlayer()
                .stream().map(pokerRoomMapper::pokerRoomToPokerRoomSlim)
                .toList();
    }


    public List<PokerRoom> findAllByScope(PokerRoomScope scope) {
        if (scope == null) {
            throw new RuntimeException("Searching for poker rooms failed. Poker room scope is null.");
        }
        switch (scope) {
            case LOCAL -> {
                return findPokerRoomsByPlayerId(userService.getLoggedUser().getId());
            }

            case GLOBAL -> {
                return findGlobalPokerRooms();
            }
        }
        throw new RuntimeException("Searching for poker rooms failed. Unrecognized poker room scope: " + scope);

    }

    public List<PokerRoomSlim> findAllSlimByScope(PokerRoomScope scope) {
        if (scope == null) {
            throw new RuntimeException("Searching for poker rooms slim failed. Poker room slim scope is null.");
        }
        return findAllByScope(scope)
                .stream()
                .map(pokerRoomMapper::pokerRoomToPokerRoomSlim)
                .toList();
    }

    public PokerRoomSlim findSlimById(Long pokerRoomSlimId) {
        if (pokerRoomSlimId == null) {
            throw new RuntimeException("Searching for poker room slim failed. Poker room slim id is null.");

        }
        return pokerRoomMapper
                .pokerRoomToPokerRoomSlim(findById(pokerRoomSlimId));
    }

}