package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.ValueUnit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PunishmentValueUnitConverter implements AttributeConverter<ValueUnit, String> {

    @Override
    public String convertToDatabaseColumn(ValueUnit valueUnit) {
        return valueUnit.name().toLowerCase();
    }

    @Override
    public ValueUnit convertToEntityAttribute(String s) {
        return ValueUnit.valueOf(s.toUpperCase());
    }
}
