package pl.coderslab.pokersessionmanager.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ClassNamesForFactory {

    POKER_ROOM("PokerRoom"),

    SESSION("Session");


    private final String value;

    @Override
    public String toString() {
        return this.value;
    }
}
