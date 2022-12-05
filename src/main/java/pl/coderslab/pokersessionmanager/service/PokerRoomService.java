package pl.coderslab.pokersessionmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.pokersessionmanager.entity.poker_room.PokerRoom;
import pl.coderslab.pokersessionmanager.repository.PokerRoomRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PokerRoomService {

    private final PokerRoomRepository pokerRoomRepository;

    public void create(PokerRoom pokerRoom) {
        pokerRoomRepository.save(pokerRoom);
    }

    ;

    public List<PokerRoom> findAll() {
        return pokerRoomRepository.findAll();
    }

    public List<PokerRoom> findPokerRoomsByUserId(Long userId) {
        return pokerRoomRepository.findPokerRoomsByUserId(userId);
    }

    public void delete(Long pokerRoomId) {
        PokerRoom pokerRoom = pokerRoomRepository.findById(pokerRoomId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert pokerRoom by pokerRoom Id."));
        pokerRoomRepository.delete(pokerRoom);
    }

    public PokerRoom findById(Long pokerRoomId) {
        PokerRoom pokerRoom = pokerRoomRepository.findById(pokerRoomId)
                .orElseThrow(() -> new RuntimeException("I can't find/convert pokerRoom by pokerRoom Id."));
        return pokerRoom;

    }

}
