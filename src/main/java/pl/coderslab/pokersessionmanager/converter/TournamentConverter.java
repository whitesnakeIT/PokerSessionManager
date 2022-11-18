package pl.coderslab.pokersessionmanager.converter;

import org.springframework.core.convert.converter.Converter;
import pl.coderslab.pokersessionmanager.entity.Tournament;

public class TournamentConverter implements Converter<Tournament, Long> {
    @Override
    public Long convert(Tournament source) {
        return null;
    }
}
