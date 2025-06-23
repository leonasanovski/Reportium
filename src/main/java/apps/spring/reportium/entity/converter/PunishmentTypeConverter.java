package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.PunishmentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PunishmentTypeConverter implements AttributeConverter <PunishmentType, String> {

    @Override
    public String convertToDatabaseColumn(PunishmentType punishmentType) {
        return punishmentType.name().toLowerCase();
    }

    @Override
    public PunishmentType convertToEntityAttribute(String s) {
        return PunishmentType.valueOf(s.toUpperCase());
    }
}
