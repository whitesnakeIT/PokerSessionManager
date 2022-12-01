package pl.coderslab.pokersessionmanager.entity.tournament;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
@Table(name = TournamentSuggestion.TABLE_NAME)
public class TournamentSuggestion extends AbstractTournament {
    public static final String TABLE_NAME = "user_suggestions_tournaments";


//    @Override
//    public boolean equals(Object o){
//        return super.equals(o);
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        TournamentSuggestion that = (TournamentSuggestion) o;
//        return getId() != null && Objects.equals(getId(), that.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}

