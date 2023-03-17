package pl.coderslab.pokersessionmanager.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.coderslab.pokersessionmanager.enums.RoleName;

@Converter(autoApply = true)
public class RoleNameEnumToColumnConverter implements AttributeConverter <RoleName,String> {
    @Override
    public String convertToDatabaseColumn(RoleName roleName) {
        if (roleName==null) {
            return null;
        }
        return roleName.toString();
    }

    @Override
    public RoleName convertToEntityAttribute(String s) {
        if (s==null) {
            return null;
        }
        return RoleName.valueOf(s);
    }
}
