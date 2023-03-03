package pl.coderslab.pokersessionmanager.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum TournamentScope {
    GLOBAL,
    LOCAL,
    ABSTRACT,
    SUGGESTION;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
