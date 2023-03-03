package pl.coderslab.pokersessionmanager.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.coderslab.pokersessionmanager.enums.PokerRoomScope;

@Converter(autoApply = true)
public class PokerRoomScopeToDbColumnConverter implements AttributeConverter<PokerRoomScope, String> {
    @Override
    public String convertToDatabaseColumn(PokerRoomScope pokerRoomScope) {
        if (pokerRoomScope==null) {
            return null;
        }
        return pokerRoomScope.toString();
    }

    @Override
    public PokerRoomScope convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return PokerRoomScope.valueOf(s.toUpperCase());
    }
}
