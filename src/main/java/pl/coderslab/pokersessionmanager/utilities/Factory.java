package pl.coderslab.pokersessionmanager.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.entity.user.Admin;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;
import pl.coderslab.pokersessionmanager.enums.UserType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Factory {

    public static AbstractTournament create(TournamentGenus tournamentGenus) {
        switch (tournamentGenus) {
            case GLOBAL -> {
                return new TournamentGlobal();
            }
            case LOCAL -> {
                return new TournamentLocal();
            }
            case SUGGESTION -> {
                return new TournamentSuggestion();
            }
            default -> throw new IllegalStateException("Unexpected value: " + tournamentGenus);
        }

    }


    public static User create(UserType userType) {
        switch (userType) {
            case ADMIN -> {
                return new Admin();
            }
            case PLAYER -> {
                return new Player();
            }
            default -> throw new IllegalStateException("Unexpected value: " + userType);
        }
    }
}
