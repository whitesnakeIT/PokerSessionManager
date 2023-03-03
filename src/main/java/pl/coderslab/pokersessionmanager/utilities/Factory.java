package pl.coderslab.pokersessionmanager.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.coderslab.pokersessionmanager.entity.PokerRoom;
import pl.coderslab.pokersessionmanager.entity.Session;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.entity.user.Admin;
import pl.coderslab.pokersessionmanager.entity.user.Player;
import pl.coderslab.pokersessionmanager.entity.user.User;
import pl.coderslab.pokersessionmanager.enums.RoleName;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;
import pl.coderslab.pokersessionmanager.enums.UserType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Factory {

    public static final String POKER_ROOM = "PokerRoom";
    public static final String SESSION = "Session";

    public static AbstractTournament create(TournamentScope tournamentScope) {
        switch (tournamentScope) {
            case GLOBAL -> {
                return new TournamentGlobal();
            }
            case LOCAL -> {
                return new TournamentLocal();
            }
            case SUGGESTION -> {
                return new TournamentSuggestion();
            }
            default ->
                    throw new IllegalStateException("I can't create Tournament. Unexpected value: " + tournamentScope);
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
            default -> throw new IllegalStateException("I can't create User. Unexpected value: " + userType);
        }
    }

    public static SimpleGrantedAuthority create(RoleName roleName) {
        switch (roleName) {
            case ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS -> {
                return new SimpleGrantedAuthority(roleName.name());
            }
            default ->
                    throw new IllegalStateException("I can't create SimpleGrantedAuthority. Unexpected value: " + roleName);
        }
    }
    public static  <T> T create(Class<T> clazz) {
        switch (clazz.getSimpleName()) {

            case POKER_ROOM -> {
                return (T) new PokerRoom();
            }

            case SESSION -> {
                return (T) new Session();
            }

            default -> throw new RuntimeException("I cant create object: " + clazz.getSimpleName());
    }}
}
