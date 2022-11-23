package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.Mapper;
import pl.coderslab.pokersessionmanager.entity.Tournament;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentForSessionDto;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.TournamentSlimDto;


import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {



    TournamentSlimDto tournamentToTournamentSlimDto(Tournament tournament);

    List<TournamentSlimDto> tournamentListToTournamentSlimDto(List<Tournament> tournamentList);


    TournamentForSessionDto tournamentToTournamentForSessionDto(Tournament tournament);


    List<TournamentForSessionDto> tournamentToTournamentForSessionDto(List<Tournament> tournament);






}
