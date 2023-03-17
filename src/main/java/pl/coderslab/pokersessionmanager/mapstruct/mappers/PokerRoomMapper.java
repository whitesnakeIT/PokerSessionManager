package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.*;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.mapstruct.dto.poker_room.PokerRoomSlim;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PokerRoomMapper {
    PokerRoom pokerRoomSlimToPokerRoom(PokerRoomSlim pokerRoomSlim);

    PokerRoomSlim pokerRoomToPokerRoomSlim(PokerRoom pokerRoom);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PokerRoom updatePokerRoomFromPokerRoomSlim(PokerRoomSlim pokerRoomSlim, @MappingTarget PokerRoom pokerRoom);
}
