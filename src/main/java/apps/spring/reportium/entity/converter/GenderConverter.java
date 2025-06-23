package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) return null;
        String toLowerCase = gender.name().toLowerCase(); //GENDER -> gender
        return Character.toUpperCase(toLowerCase.charAt(0)) + toLowerCase.substring(1); // Gender
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        if (s == null) return null;
        return Gender.valueOf(s.toUpperCase());
    }
}
