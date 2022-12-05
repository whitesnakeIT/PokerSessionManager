package pl.coderslab.pokersessionmanager.entity.poker_room;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
//@Table(name = PokerRoomGlobal.TABLE_NAME)
@DiscriminatorValue(value = PokerRoomGlobal.POKER_ROOM_SCOPE)
public class PokerRoomGlobal extends PokerRoom {
    public static final String POKER_ROOM_SCOPE = "global";
}
