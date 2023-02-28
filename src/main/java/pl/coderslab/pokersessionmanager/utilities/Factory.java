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
import pl.coderslab.pokersessionmanager.enums.TournamentGenus;
import pl.coderslab.pokersessionmanager.enums.UserType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Factory implements AbstractFactory {

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
            default ->
                    throw new IllegalStateException("I can't create Tournament. Unexpected value: " + tournamentGenus);
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

    //    public static <T> T create(EntityNames entityNames){
//        if (entityNames.name().equalsIgnoreCase(SESSION)) {
//            return (T) new Session();
//        }
//
//
//    }
    public static PokerRoom createPokerRoom() {
        return new PokerRoom();
    }

    public static Session createSession() {
        return new Session();
    }
}
