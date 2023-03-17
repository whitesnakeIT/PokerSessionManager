package pl.coderslab.pokersessionmanager.mapstruct.mappers;

import org.mapstruct.Mapper;
import pl.coderslab.pokersessionmanager.entity.tournament.AbstractTournament;
import pl.coderslab.pokersessionmanager.mapstruct.dto.tournament.AbstractTournamentToSlimTournament;

@Mapper(componentModel = "spring")
public interface TournamentMapper {


    AbstractTournamentToSlimTournament abstractTournamentToAbstractTournamentDto(AbstractTournament abstractTournament);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    AbstractTournament updateAbstractTournamentFromAbstractTournamentDto(AbstractTournamentToSlimTournament abstractTournamentToSlimTournament, @MappingTarget AbstractTournament abstractTournament);
}



