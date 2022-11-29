package pl.coderslab.pokersessionmanager.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.pokersessionmanager.entity.tournament.TournamentGlobal;
import pl.coderslab.pokersessionmanager.service.TournamentService;

public class TournamentConverter implements Converter<TournamentGlobal, Long> {
    @Autowired
    TournamentService tournamentService;
    @Override
    public Long convert(TournamentGlobal source) {
        return null;
    }
}
