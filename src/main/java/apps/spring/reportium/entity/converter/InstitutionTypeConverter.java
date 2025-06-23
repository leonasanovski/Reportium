package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.InstitutionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class InstitutionTypeConverter implements AttributeConverter <InstitutionType, String>{
    @Override
    public String convertToDatabaseColumn(InstitutionType institutionType) {
        if (institutionType == null) return null;
        String [] strings = institutionType.name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string.charAt(0)).append(string.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public InstitutionType convertToEntityAttribute(String s) {
        if (s == null) return null;
        return InstitutionType.valueOf(s.replaceAll(" ", "_").toUpperCase());
    }
}
