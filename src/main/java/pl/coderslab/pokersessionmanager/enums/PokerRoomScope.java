package pl.coderslab.pokersessionmanager.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum PokerRoomScope {
    LOCAL,
    GLOBAL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
