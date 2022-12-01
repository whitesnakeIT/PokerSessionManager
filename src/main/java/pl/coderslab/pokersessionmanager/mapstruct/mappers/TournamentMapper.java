package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.Mapper;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentLocal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {


    TournamentSlimDto tournamentToTournamentSlimDto(TournamentGlobal tournament);

    List<TournamentSlimDto> tournamentToTournamentSlimDto(List<TournamentGlobal> tournamentList);

    TournamentSlimDto tournamentLocalToTournamentSlimDto(TournamentLocal tournamentLocal);

    List<TournamentSlimDto> tournamentLocalToTournamentSlimDto(List<TournamentLocal> tournaments);

    TournamentGlobal tournamentSuggestionToTournament(TournamentSuggestion tournamentSuggestion);

    List<TournamentGlobal> tournamentSuggestionToTournament(List<TournamentSuggestion> tournamentSuggestionList);

    TournamentSuggestion tournamentToTournamentSuggestion(TournamentGlobal tournament);

    List<TournamentSuggestion> tournamentToTournamentSuggestion(List<TournamentGlobal> tournaments);

    TournamentGlobal tournamentLocalToTournament(TournamentLocal tournamentLocal);

    List<TournamentGlobal> tournamentLocalToTournament(List<TournamentLocal> tournamentLocalList);

    TournamentLocal tournamentToTournamentLocal(TournamentGlobal tournament);

    List<TournamentLocal> tournamentToTournamentLocal(List<TournamentGlobal> tournaments);

}



