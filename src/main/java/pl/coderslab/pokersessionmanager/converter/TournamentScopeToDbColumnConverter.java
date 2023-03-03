package pl.coderslab.pokersessionmanager.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.coderslab.pokersessionmanager.enums.TournamentScope;

@Converter(autoApply = true)
public class TournamentScopeToDbColumnConverter implements AttributeConverter <TournamentScope,String> {
    @Override
    public String convertToDatabaseColumn(TournamentScope tournamentScope) {
        if (tournamentScope==null){
            return null;
        }
        return tournamentScope.toString();
    }

    @Override
    public TournamentScope convertToEntityAttribute(String s) {
        if(s==null){
            return null;
        }
        return TournamentScope.valueOf(s.toUpperCase());
    }
}
