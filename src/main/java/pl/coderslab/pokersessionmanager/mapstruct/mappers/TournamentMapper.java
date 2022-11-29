package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentSuggestion;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentForSessionDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSuggestionDto;


import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {


    TournamentSlimDto tournamentToTournamentSlimDto(TournamentGlobal tournament);

    List<TournamentSlimDto> tournamentListToTournamentSlimDto(List<TournamentGlobal> tournamentList);


    TournamentForSessionDto tournamentToTournamentForSessionDto(TournamentGlobal tournament);


    List<TournamentForSessionDto> tournamentToTournamentForSessionDto(List<TournamentGlobal> tournament);


    TournamentGlobal tournamentSuggestionToTournament(TournamentSuggestion tournamentSuggestion);

    TournamentSuggestion tournamentToTournamentSuggestion(TournamentGlobal tournament);


    TournamentGlobal updateTournamentFromTournamentSuggestion(TournamentSuggestion tournamentSuggestion, @MappingTarget TournamentGlobal tournamentGlobal);
}
