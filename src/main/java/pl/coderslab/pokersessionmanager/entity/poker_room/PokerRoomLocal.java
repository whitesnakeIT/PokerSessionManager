package pl.coderslab.pokersessionmanager.entity.poker_room;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.pokersessionmanager.entity.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter

@ToString(callSuper = true)
//@Table(name = PokerRoomLocalController.TABLE_NAME)
@DiscriminatorValue(value = PokerRoomLocal.POKER_ROOM_SCOPE)
public class PokerRoomLocal extends PokerRoom {
    public static final String POKER_ROOM_SCOPE = "local";

    @ManyToOne
    @ToString.Exclude
    private User user;
}
