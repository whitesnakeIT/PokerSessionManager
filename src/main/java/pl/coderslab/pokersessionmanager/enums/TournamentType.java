package pl.coderslab.pokersessionmanager.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum TournamentType {
    NORMAL,
    PROGRESSIVE_KNOCKOUT,
    KNOCKOUT,
    ZOOM
}
